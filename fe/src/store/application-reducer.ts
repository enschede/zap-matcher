import {ActionReducer, combineReducers} from '@ngrx/store';
import {ApplicationState} from './application-state';
import {account} from './account/account-reducer';
import {profile} from './profile/profile-reducer';
import {registration} from './registration/registration-reducer';
import {routerReducer} from '@ngrx/router-store';

export const application: ActionReducer<ApplicationState> =
  combineReducers({
    account,
    profile,
    registration,
    router: routerReducer
  });

