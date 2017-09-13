import {Action, ActionReducer} from '@ngrx/store';
import {
  REGISTRATION_FAILED_ACTION, REGISTRATION_STARTED_ACTION, REGISTRATION_SUCCEEDED_ACTION,
  RegistrationFailedAction, RegistrationStartedAction, RegistrationSucceededAction
} from './registration-actions';
import {RegistrationState, INITIAL_REGISTRATION_STATE} from './registration-state';

const registrationStartedAction: ActionReducer<RegistrationState> = (state: RegistrationState, action: RegistrationStartedAction) => {
  return Object.assign({}, state, {
    isRegistrating: true,
    registrationMail: action.payload.emailaddress,
    registrationPassword: action.payload.password
  });
};

const registrationSucceededAction: ActionReducer<RegistrationState> = (state: RegistrationState, action: RegistrationSucceededAction) => {
  return Object.assign({}, state, {
    isRegistrating: false,
    registrationSuccess: true
  });
};

const registrationFailedAction: ActionReducer<RegistrationState> = (state: RegistrationState, action: RegistrationFailedAction) => {
  return Object.assign({}, state, {
    isRegistrating: false,
    registrationSuccess: false
  });
};

export const registration: ActionReducer<RegistrationState> = (state = INITIAL_REGISTRATION_STATE, action: Action) => {
  switch (action.type) {
    case REGISTRATION_STARTED_ACTION:
      return registrationStartedAction(state, action);

    case REGISTRATION_SUCCEEDED_ACTION:
      return registrationSucceededAction(state, action);

    case REGISTRATION_FAILED_ACTION:
      return registrationFailedAction(state, action);

    default:
      return state;
  }
};
