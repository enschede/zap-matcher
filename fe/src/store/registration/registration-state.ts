export interface RegistrationState {
  isRegistrating: boolean;
  registrationSuccess: boolean;
  registrationMail: String;
  registrationPassword: String;
}

export const INITIAL_REGISTRATION_STATE: RegistrationState = {
  isRegistrating: undefined,
  registrationSuccess: undefined,
  registrationMail: undefined,
  registrationPassword: undefined
};
