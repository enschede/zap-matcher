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
  registerForm: FormGroup;
  registrationSubscription: Subscription;

  constructor(private registrationService: RegistrationService,
              private rememberMeService: RememberMeService,
              private router: Router) {

    this.registerForm = new FormGroup({
      emailaddress: new FormControl(null, [Validators.required, Validators.minLength(5), Validators.email]),
      password: new FormControl(null, [Validators.required, Validators.minLength(5)]),
      password2: new FormControl(null, [Validators.required, Validators.minLength(5)])
    });
  }

  onSubmit(): boolean {
    if (this.registerForm.valid) {
      const command = new UserRegisterCommand(
        this.registerForm.value['emailaddress'],
        this.registerForm.value['password'],
        this.registerForm.value['password2']
      );
      console.log('Submitting' + command);
      this.registrationSubscription =
        this.registrationService.register(command).subscribe((json: any) => {
          this.rememberMeService.remember(json.emailaddress);
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
