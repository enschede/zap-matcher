import {Component, OnDestroy} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {RegistrationService, RememberMeService} from '../../service/index';
import {UserRegisterCommand} from '../../command/index';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnDestroy {
  form: FormGroup;
  registrationSubscription: Subscription;

  constructor(private registrationService: RegistrationService,
              private rememberMeService: RememberMeService,
              private router: Router) {

    this.form = new FormGroup({
      emailaddress: new FormControl(null, [Validators.required, Validators.minLength(5), Validators.email]),
      password: new FormControl(null, [Validators.required, Validators.minLength(5)]),
      password2: new FormControl(null, [Validators.required])
    });
  }

  onSubmit(): boolean {
    if (this.form.valid) {
      const command = new UserRegisterCommand(
        this.form.value['emailaddress'],
        this.form.value['password'],
        this.form.value['password2']
      );
      console.log('Submitting' + command);
      this.registrationSubscription =
        this.registrationService.register(command).subscribe((json: any) => {
          this.rememberMeService.rememberUsername(json.emailaddress);
          this.rememberMeService.rememberPassword(command.password);
          this.router.navigate(['login']);
        });
    }

    return false;
  }

  ngOnDestroy() {
    if (this.registrationSubscription) {
      this.registrationSubscription.unsubscribe();
      this.registrationSubscription = null;
    }
  }
}
