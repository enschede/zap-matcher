import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoginService, RememberMeService} from '../../service/index';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form: FormGroup;

  constructor(private loginService: LoginService, private rememberMeService: RememberMeService) {
    const emailaddress = this.rememberMeService.recall();

    this.form = new FormGroup({
      emailaddress: new FormControl(emailaddress, [Validators.required, Validators.minLength(5), Validators.email]),
      password: new FormControl(null, [Validators.required, Validators.minLength(5)])
    });
  }

}
