import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {LoginService, RegistrationService, RememberMeService} from '../service/index';
import {CombinedLoginRegistrationComponent, LoginComponent, RegistrationComponent} from '../component/index';
import {appRoutingProviders, routing} from './app.routes';

const PROVIDERS = [
  appRoutingProviders,
  LoginService, RegistrationService, RememberMeService
];

@NgModule({
  declarations: [
    AppComponent,
    CombinedLoginRegistrationComponent,
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
