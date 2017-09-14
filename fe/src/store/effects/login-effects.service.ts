import { Injectable } from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Observable} from 'rxjs';
import {Action} from '@ngrx/store';
import {LOGIN_STARTED_ACTION, LoginFailedAction, LoginSucceededAction} from '../account/account-actions';
import {LoginService} from '../../service/login/login.service';

@Injectable()
export class LoginEffectsService {
  constructor(private actions$: Actions, private loginService: LoginService) {}

  @Effect() loginActions$: Observable<Action> = this.actions$
    .ofType(LOGIN_STARTED_ACTION)
    .do(action => console.log(action))
    .switchMap(action => this.loginService.login(action.payload))
    .do(result => console.log(result))
    .map(result => new LoginSucceededAction(result))
    .catch(error => Observable.of(new LoginFailedAction(error)));
}
