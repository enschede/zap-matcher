import {Action} from '@ngrx/store';
import {LoginCommand, RegistrationCommand} from './account-commands';

export const REGISTRATION_STARTED_ACTION = 'REGISTRATION_STARTED_ACTION';

export class RegistrationStartedAction implements Action {
  readonly type = REGISTRATION_STARTED_ACTION;

  constructor(public payload?: RegistrationCommand) {}
}

export const REGISTRATION_SUCCEEDED_ACTION = 'REGISTRATION_SUCCEEDED_ACTION';

export class RegistrationSucceededAction implements Action {
  readonly type = REGISTRATION_SUCCEEDED_ACTION;

  constructor(public payload: any) {}
}

export const LOGIN_STARTED_ACTION = 'LOGIN_STARTED_ACTION';

export class LoginStartedAction implements Action {
  readonly type = LOGIN_STARTED_ACTION;

  constructor(public payload?: LoginCommand) {}
}

export const LOGIN_SUCCEEDED_ACTION = 'LOGIN_SUCCEEDED_ACTION';

export class LoginSucceededAction implements Action {
  readonly type = LOGIN_SUCCEEDED_ACTION;

  constructor(public payload: any) {}
}
