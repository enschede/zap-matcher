export interface AccountState {
  isLoggingIn: boolean;
  isAuthenticated: boolean;
  username: string;
  emailAddress: string;
}

export const INITIAL_ACCOUNT_STATE: AccountState = {
  isLoggingIn: undefined,
  isAuthenticated: undefined,
  username: undefined,
  emailAddress: undefined
};
