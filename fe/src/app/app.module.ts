import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {LoginService, ProfileService, RegistrationService} from '../service/index';
import {AccountPageComponent, LoginPageComponent, WelcomePageComponent} from '../page/index';
import {LoginComponent, RegistrationComponent} from '../component/index';
import {appRoutingProviders, routing} from './app.routes';
import {compose} from '@ngrx/core';
import {StoreModule} from '@ngrx/store';
import {EffectsModule} from '@ngrx/effects';
import {LoginEffectsService} from '../store/effects/login-effects.service';
import {RegistrationEffectsService} from '../store/effects/registration-effects.service';
import {application} from '../store/application-reducer';
import {INITIAL_APPLICATION_STATE} from '../store/application-state';
import {storeFreeze} from 'ngrx-store-freeze';
import {environment} from '../environments/environment';

const PROVIDERS = [
  appRoutingProviders,
  LoginService,
  ProfileService,
  RegistrationService
];

export function applicationReducer(state: any = INITIAL_APPLICATION_STATE, action: any) {
  const combinedReducers = application(state, action);
  if (environment.production) {
    return combinedReducers;
  }
  return compose(storeFreeze, combinedReducers);
}

@NgModule({
  declarations: [
    AppComponent,
    AccountPageComponent,
    LoginPageComponent,
    WelcomePageComponent,
    LoginComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    StoreModule.provideStore(applicationReducer),
    EffectsModule.run(LoginEffectsService),
    EffectsModule.run(RegistrationEffectsService),
    routing
  ],
  providers: PROVIDERS,
  bootstrap: [AppComponent]
})
export class AppModule { }
