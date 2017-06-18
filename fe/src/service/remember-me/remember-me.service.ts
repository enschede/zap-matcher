import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable()
export class RememberMeService {
  emailaddress$: BehaviorSubject<string>;

  constructor() {
    this.emailaddress$ = new BehaviorSubject(null);
  }

  remember(emailaddress: string) {
    this.emailaddress$.next(emailaddress);
  }

  forget() {
    this.emailaddress$.next(null);
  }

  recall(): string {
    return this.emailaddress$.getValue();
  }
}
