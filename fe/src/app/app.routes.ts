import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CombinedLoginRegistrationComponent, LoginComponent} from '../component/index';


const appRoutes: Routes = [
  {path: '', component: CombinedLoginRegistrationComponent},
  {path: 'login', component: LoginComponent}
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
