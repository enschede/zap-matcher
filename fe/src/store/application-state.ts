import {AccountState, INITIAL_ACCOUNT_STATE} from './account/account-state';

export interface ApplicationState {
  account: AccountState;
}

export const INITIAL_APPLICATION_STATE: ApplicationState = {
  account: INITIAL_ACCOUNT_STATE
};
