import {Action} from '@ngrx/store';
import {ApplicationState, INITIAL_APPLICATION_STATE} from './application-state';
import {REGISTRATION_SUCCEEDED_ACTION, RegistrationSucceededAction} from './account/account-actions';


export function applicationReducer(state: ApplicationState = INITIAL_APPLICATION_STATE, action: Action): ApplicationState {

  switch (action.type) {
    case REGISTRATION_SUCCEEDED_ACTION:
      return handleRegistrationSucceededAction(state, <any>action);

    default:
      return state;
  }
}

function handleRegistrationSucceededAction(state: ApplicationState, action: RegistrationSucceededAction): ApplicationState {

  const newState = Object.assign({}, state);
  return newState;
}
