import {Action, ActionReducer} from '@ngrx/store';
import {UPDATE_PROFILE_STARTED_ACTION} from './profile-actions';
import {INITIAL_PROFILE_STATE, ProfileState} from './profile-state';

const updateProfileStarted = (state, action: Action) => {
  return Object.assign({}, state, {isLoggingIn: true});
};

export const profile: ActionReducer<ProfileState> = (state = INITIAL_PROFILE_STATE, action: Action = {type: '', payload: 0}) => {
  switch (action.type) {
    case UPDATE_PROFILE_STARTED_ACTION:
      return updateProfileStarted(state, action);


    default:
      return state;
  }
};
