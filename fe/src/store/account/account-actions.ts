import {Action} from '@ngrx/store';

export const LOGIN_STARTED_ACTION = 'LOGIN_STARTED_ACTION';

export class LoginStartedPayload {
  constructor(public username: string, public password: string) {}

}

export class LoginStartedAction implements Action {
  readonly type = LOGIN_STARTED_ACTION;

  constructor(public payload?: LoginStartedPayload) {}
}

export const LOGIN_SUCCEEDED_ACTION = 'LOGIN_SUCCEEDED_ACTION';

export class LoginSucceededPayload {
  constructor(public emailaddress: string) {}
}

export class LoginSucceededAction implements Action {
  readonly type = LOGIN_SUCCEEDED_ACTION;

  constructor(public payload: LoginSucceededPayload) {}
}

export const LOGIN_FAILED_ACTION = 'LOGIN_FAILED_ACTION';

export class LoginFailedAction implements Action {
  readonly type = LOGIN_FAILED_ACTION;

  constructor(public payload: any) {}
}
