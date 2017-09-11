import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Store} from '@ngrx/store';
import {ApplicationState} from '../../store/application-state';
import {RegistrationStartedAction} from '../../store/account/account-actions';
import {RegistrationCommand} from '../../store/account/account-commands';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  form: FormGroup;

  constructor(private store: Store<ApplicationState>,
              private router: Router) {

    this.form = new FormGroup({
      emailaddress: new FormControl(null, [Validators.required, Validators.minLength(5), Validators.email]),
      password: new FormControl(null, [Validators.required, Validators.minLength(5)]),
      password2: new FormControl(null, [Validators.required])
    });
  }

  onSubmit(): boolean {
    if (this.form.valid) {
      const action = new RegistrationStartedAction(
        new RegistrationCommand(
          this.form.value['emailaddress'],
          this.form.value['password'],
          this.form.value['password2']
        )
      );
      console.log('Submitting' + action);
      this.store.dispatch(action);
          // this.router.navigate(['login']);
    }

    return false;
  }
}
