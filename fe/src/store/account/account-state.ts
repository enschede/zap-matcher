export interface AccountState {
  username: string;
  emailaddress: string;
}

export const INITIAL_ACCOUNT_STATE: AccountState = {
  username: undefined,
  emailaddress: undefined
};
