import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AccountPageComponent, LoginPageComponent, WelcomePageComponent} from '../page/index';


const appRoutes: Routes = [
  {path: '', component: WelcomePageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'account', component: AccountPageComponent}
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
