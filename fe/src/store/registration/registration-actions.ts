import {Action} from '@ngrx/store';

export const REGISTRATION_STARTED_ACTION = 'REGISTRATION_STARTED_ACTION';

export class RegistrationStartedPayload {
  constructor(public emailaddress: string, public password: string, public password2: string) {}

}
export class RegistrationStartedAction implements Action {
  readonly type = REGISTRATION_STARTED_ACTION;

  constructor(public payload?: RegistrationStartedPayload) {}
}

export const REGISTRATION_SUCCEEDED_ACTION = 'REGISTRATION_SUCCEEDED_ACTION';

export interface RegistrationSucceededPayload {
  emailaddress: string;
}

export class RegistrationSucceededAction implements Action {
  readonly type = REGISTRATION_SUCCEEDED_ACTION;

  constructor(public payload: RegistrationSucceededPayload) {}
}

export const REGISTRATION_FAILED_ACTION = 'REGISTRATION_FAILED_ACTION';

export class RegistrationFailedAction implements Action {
  readonly type = REGISTRATION_FAILED_ACTION;

  constructor(public payload: any) {}
}
