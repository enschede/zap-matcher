import {AccountState, INITIAL_ACCOUNT_STATE} from './account/account-state';
import {ProfileState, INITIAL_PROFILE_STATE} from './profile/profile-state';

export interface ApplicationState {
  account: AccountState;
  profile: ProfileState;
}

export const INITIAL_APPLICATION_STATE: ApplicationState = {
  account: INITIAL_ACCOUNT_STATE,
  profile: INITIAL_PROFILE_STATE
};
