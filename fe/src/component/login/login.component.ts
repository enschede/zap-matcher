import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ApplicationState} from '../../store/application-state';
import {Store} from '@ngrx/store';
import {LoginStartedAction, LoginStartedPayload} from '../../store/account/account-actions';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  @Input() autoLogin = false;
  autofill = false;

  constructor(private store: Store<ApplicationState>,
              private router: Router) {
    const username = undefined; // this.rememberMeService.recallUsername();
    const password = undefined;
    this.autofill = !!username && !!password;

    this.form = new FormGroup({
      username: new FormControl(username, [Validators.required, Validators.minLength(5), Validators.email]),
      password: new FormControl(password, [Validators.required, Validators.minLength(5)])
    });
  }

  onSubmit(): boolean {
    if (this.form.valid) {
      const action = new LoginStartedAction(new LoginStartedPayload(
          this.form.value['username'],
          this.form.value['password']
        )
      );
      this.store.dispatch(action);
    }

    return false;
  }

  ngOnInit(): void {
    if (this.autoLogin && this.autofill) {
      this.onSubmit();
    }
  }
}
