import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable()
export class RememberMeService {
  username$: BehaviorSubject<string>;
  password$: BehaviorSubject<string>;

  constructor() {
    this.username$ = new BehaviorSubject(null);
    this.password$ = new BehaviorSubject(null);
  }

  rememberUsername(username: string) {
    this.username$.next(username);
  }

  forgetUsername() {
    this.username$.next(null);
  }

  recallUsername(): string {
    return this.username$.getValue();
  }

  rememberPassword(password: string) {
    this.password$.next(password);
  }

  forgetPassword() {
    this.password$.next(null);
  }

  recallPassword(): string {
    return this.password$.getValue();
  }
}
