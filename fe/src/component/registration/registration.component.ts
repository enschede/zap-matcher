import {Component, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {RegistrationService} from '../../service/index';
import {UserRegisterCommand} from '../../command/index';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  @ViewChild('registerForm') registerForm: NgForm;
  emailaddress: string;
  password: string;
  password2: string;
  registrationSubscription: Subscription;

  constructor(private registrationService: RegistrationService) {}

  onSubmit(): boolean {
    if (this.registerForm.valid) {
      const command = new UserRegisterCommand(
        this.emailaddress,
        this.password,
        this.password2
      );
      console.log('Submitting' + command);
      this.registrationSubscription =
        this.registrationService.register(command).subscribe(() => {
        });
    }

    return false;
  }
}
