export interface AccountState {
  isLoggingIn: boolean;
  username: string;
  emailaddress: string;
}

export const INITIAL_ACCOUNT_STATE: AccountState = {
  isLoggingIn: undefined,
  username: undefined,
  emailaddress: undefined
};
