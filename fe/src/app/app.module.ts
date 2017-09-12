import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {LoginService, ProfileService, RegistrationService} from '../service/index';
import {AccountPageComponent, LoginPageComponent, WelcomePageComponent} from '../page/index';
import {LoginComponent, RegistrationComponent} from '../component/index';
import {appRoutingProviders, routing} from './app.routes';
import {combineReducers, StoreModule} from '@ngrx/store';
import {EffectsModule} from '@ngrx/effects';
import {LoginEffectsService} from '../store/effects/login-effects.service';
import {RegistrationEffectsService} from '../store/effects/registration-effects.service';
import {account} from '../store/account/account-reducer';
import {profile} from '../store/profile/profile-reducer';

const PROVIDERS = [
  appRoutingProviders,
  LoginService,
  ProfileService,
  RegistrationService
];

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
    StoreModule.provideStore(),
    EffectsModule.run(LoginEffectsService),
    EffectsModule.run(RegistrationEffectsService),
    routing
  ],
  providers: PROVIDERS,
  bootstrap: [AppComponent]
})
export class AppModule { }
