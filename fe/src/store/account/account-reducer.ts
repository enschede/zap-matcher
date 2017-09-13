import {Action, ActionReducer} from '@ngrx/store';
import {
  LOGIN_FAILED_ACTION, LOGIN_STARTED_ACTION, LOGIN_SUCCEEDED_ACTION,
  LoginFailedAction, LoginStartedAction, LoginSucceededAction,
} from './account-actions';
import {AccountState, INITIAL_ACCOUNT_STATE} from './account-state';


const loginStartedAction: ActionReducer<AccountState> = (state: AccountState, action: LoginStartedAction) => {
  return Object.assign({}, state, {
    isLoggingIn: true,
    username: action.payload.username
  });
};

const loginSucceededAction: ActionReducer<AccountState> = (state: AccountState, action: LoginSucceededAction) => {
  return Object.assign({}, state, {
    isLoggingIn: false,
    loginSuccess: true,
    emailAddress: action.payload.emailaddress
  });
};

const loginFailedAction: ActionReducer<AccountState> = (state: AccountState, action: LoginFailedAction) => {
  return Object.assign({}, state, {
    isLoggingIn: false,
    loginSuccess: true,
    username: undefined,
    emailAddress: undefined
  });
};

export const account: ActionReducer<AccountState> = (state = INITIAL_ACCOUNT_STATE, action: Action) => {
  switch (action.type) {
    case LOGIN_STARTED_ACTION:
      return loginStartedAction(state, action);

    case LOGIN_SUCCEEDED_ACTION:
      return loginSucceededAction(state, action);

    case LOGIN_FAILED_ACTION:
      return loginFailedAction(state, action);

    default:
      return state;
  }
};
