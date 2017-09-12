import {Action, ActionReducer} from '@ngrx/store';
import {LOGIN_STARTED_ACTION, LOGIN_SUCCEEDED_ACTION} from './account-actions';
import {AccountState, INITIAL_ACCOUNT_STATE} from './account-state';

const accountLoginStarted = (state, action: Action) => {
  return Object.assign({}, state, {isLoggingIn: true});
};

const accountLoginSucceeded = (state, action: Action) => {
  return Object.assign({}, state, {isLoggingIn: false});
};

const accountLoginFailed = (state, action: Action) => {
  return Object.assign({}, state, {isLoggingIn: false});
};

export const account: ActionReducer<AccountState> = (state = INITIAL_ACCOUNT_STATE, action: Action) => {
  switch (action.type) {
    case LOGIN_STARTED_ACTION:
      return accountLoginStarted(state, action);

    case LOGIN_SUCCEEDED_ACTION:
      return accountLoginSucceeded(state, action);

    case 'ACCOUNT_LOGIN_FAILED':
      return accountLoginFailed(state, action);

    default:
      return state;
  }
};
