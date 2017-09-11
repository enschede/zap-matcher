import { NgModule } from '@angular/core';
import { StoreModule } from '@ngrx/store';
import { account } from './account/account-reducer';

// TODO upgrade to ngrx version 4 !!

@NgModule({
  imports: [StoreModule.provideStore({account})]
})
export class AppStoreModule {
}
