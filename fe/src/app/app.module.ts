import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {LoginService, ProfileService, RegistrationService, RememberMeService} from '../service/index';
import {AccountPageComponent, LoginPageComponent, WelcomePageComponent} from '../page/index';
import {LoginComponent, RegistrationComponent} from '../component/index';
import {appRoutingProviders, routing} from './app.routes';

const PROVIDERS = [
  appRoutingProviders,
  LoginService,
  ProfileService,
  RegistrationService,
  RememberMeService
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
    routing
  ],
  providers: PROVIDERS,
  bootstrap: [AppComponent]
})
export class AppModule { }
