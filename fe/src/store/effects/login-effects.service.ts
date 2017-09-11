import { Injectable } from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Observable} from 'rxjs';
import {Action} from '@ngrx/store';
import {LOGIN_STARTED_ACTION, LoginSucceededAction} from '../account/account-actions';
import {LoginService} from '../../service/login/login.service';

@Injectable()
export class LoginEffectsService {
  constructor(private actions$: Actions, private loginService: LoginService) {}

  @Effect() loginActions$: Observable<Action> = this.actions$
    .ofType(LOGIN_STARTED_ACTION)
    .switchMap(action => this.loginService.login(action.payload))
    .map(result => new LoginSucceededAction(result));
}
