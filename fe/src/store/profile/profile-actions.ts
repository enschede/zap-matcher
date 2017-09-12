import {Action} from '@ngrx/store';
import {UpdateProfileCommand} from './profile-commands';

export const UPDATE_PROFILE_STARTED_ACTION = 'UPDATE_PROFILE_STARTED_ACTION';

export class UpdateProfileStartedAction implements Action {
  readonly type = UPDATE_PROFILE_STARTED_ACTION;

  constructor(public payload?: UpdateProfileCommand) {}
}
