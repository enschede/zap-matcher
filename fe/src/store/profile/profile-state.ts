export interface ProfileState {
  username: string;
  emailaddress: string;
}

export const INITIAL_PROFILE_STATE: ProfileState = {
  username: undefined,
  emailaddress: undefined
};
