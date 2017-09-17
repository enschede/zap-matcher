import {ChangeDetectionStrategy, Component} from '@angular/core';

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WelcomePageComponent {

}
