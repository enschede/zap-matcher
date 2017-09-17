import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AccountPageComponent, LoginPageComponent, WelcomePageComponent} from '../page/index';
import {AuthGuardService} from '../service/auth-guard/auth-guard.service';


const appRoutes: Routes = [
  {path: '', component: WelcomePageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'account', component: AccountPageComponent, canActivate: [AuthGuardService]}
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
