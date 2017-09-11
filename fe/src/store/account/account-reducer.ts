import {Action} from '@ngrx/store';

const accountLoginStarted = (state, action: Action) => {
  return Object.assign({}, state, {isLoggingIn: true});
};

const accountLoginSucceeded = (state, action: Action) => {
  return Object.assign({}, state, {isLoggingIn: false});
};

const accountLoginFailed = (state, action: Action) => {
  return Object.assign({}, state, {isLoggingIn: false});
};

export const account = (state = {}, action: Action = {type: '', payload: 0}) => {
  switch (action.type) {
    case 'ACCOUNT_LOGIN_STARTED':
      return accountLoginStarted(state, action);

    case 'ACCOUNT_LOGIN_SUCCEEDED':
      return accountLoginSucceeded(state, action);

    case 'ACCOUNT_LOGIN_FAILED':
      return accountLoginFailed(state, action);

    default:
      return state;
  }
};
