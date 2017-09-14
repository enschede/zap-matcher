import {AccountState, INITIAL_ACCOUNT_STATE} from './account/account-state';
import {ProfileState, INITIAL_PROFILE_STATE} from './profile/profile-state';
import {INITIAL_REGISTRATION_STATE, RegistrationState} from './registration/registration-state';
import {RouterState} from '@ngrx/router-store';

export interface ApplicationState {
  account: AccountState;
  profile: ProfileState;
  registration: RegistrationState;
  router: RouterState;
}

export const INITIAL_APPLICATION_STATE: ApplicationState = {
  account: INITIAL_ACCOUNT_STATE,
  profile: INITIAL_PROFILE_STATE,
  registration: INITIAL_REGISTRATION_STATE,
  router: {path: ''}
};
