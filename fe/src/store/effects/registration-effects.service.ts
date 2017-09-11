import { Injectable } from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Observable} from 'rxjs';
import {Action} from '@ngrx/store';
import {RegistrationService} from '../../service/registration/registration.service';
import {REGISTRATION_STARTED_ACTION, RegistrationSucceededAction} from '../account/account-actions';

@Injectable()
export class RegistrationEffectsService {
  constructor(private actions$: Actions, private registrationService: RegistrationService) {}

  @Effect() registrationActions$: Observable<Action> = this.actions$
    .ofType(REGISTRATION_STARTED_ACTION)
    .switchMap(action => this.registrationService.register(action.payload))
    .map(result => new RegistrationSucceededAction(result));
}
