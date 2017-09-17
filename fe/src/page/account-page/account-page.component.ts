import {ChangeDetectionStrategy, Component} from '@angular/core';
import {Store} from '@ngrx/store';
import {ApplicationState} from '../../store/application-state';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'app-account-page',
  templateUrl: './account-page.component.html',
  styleUrls: ['./account-page.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AccountPageComponent {
  username$: Observable<string>;

  constructor(private store: Store<ApplicationState>) {
    this.username$ = this.store.select(state => state.account.username);
  }

}
