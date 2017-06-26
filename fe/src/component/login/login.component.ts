import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {LoginService, RememberMeService} from '../../service/index';
import {UserLoginCommand} from '../../command/index';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnDestroy, OnInit {
  form: FormGroup;
  loginSubscription: Subscription;
  @Input() autoLogin = false;
  autofill = false;

  constructor(private loginService: LoginService,
              private rememberMeService: RememberMeService,
              private router: Router) {
    const username = this.rememberMeService.recallUsername();
    const password = this.rememberMeService.recallPassword();
    this.autofill = !!username && !!password;

    this.form = new FormGroup({
      username: new FormControl(username, [Validators.required, Validators.minLength(5), Validators.email]),
      password: new FormControl(password, [Validators.required, Validators.minLength(5)])
    });
  }

  onSubmit(): boolean {
    if (this.form.valid) {
      const command = new UserLoginCommand(
        this.form.value['username'],
        this.form.value['password']
      );
      console.log('Submitting' + command);
      this.loginSubscription =
        this.loginService.login(command).subscribe((json: any) => {
          if (!this.autoLogin) {
            this.rememberMeService.rememberUsername(command.username);
            this.rememberMeService.rememberPassword(command.password);
          }
          this.router.navigate(['account']);
        });
    }

    return false;
  }

  ngOnInit(): void {
    if (this.autoLogin && this.autofill) {
      this.onSubmit();
    }
  }

  ngOnDestroy() {
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe();
      this.loginSubscription = null;
    }
  }

}
