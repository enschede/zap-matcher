
export class RegistrationCommand {
  constructor(public emailaddress: string, public password: string, public password2: string) {}

}


export class LoginCommand {
  constructor(public username: string, public password: string) {}

}
