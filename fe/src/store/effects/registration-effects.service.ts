import { Injectable } from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Observable} from 'rxjs';
import {Action} from '@ngrx/store';
import {RegistrationService} from '../../service/registration/registration.service';
import {
  REGISTRATION_STARTED_ACTION, REGISTRATION_SUCCEEDED_ACTION, RegistrationFailedAction,
  RegistrationSucceededAction
} from '../registration/registration-actions';
import {Router} from '@angular/router';

@Injectable()
export class RegistrationEffectsService {
  constructor(private actions$: Actions, private registrationService: RegistrationService, private router: Router) {}

  @Effect() registrationActionsStarted$: Observable<Action> = this.actions$
    .ofType(REGISTRATION_STARTED_ACTION)
    .do(action => console.log(action))
    .switchMap(action => this.registrationService.register(action.payload))
    .do(result => console.log(result))
    .map(result => new RegistrationSucceededAction(result))
    .catch(error => Observable.of(new RegistrationFailedAction(error)));

  @Effect() registrationActionsSucceeded$: Observable<any> = this.actions$
    .ofType(REGISTRATION_SUCCEEDED_ACTION)
    .do(action => console.log(action))
    .switchMap(() => this.router.navigate(['login']));

}
