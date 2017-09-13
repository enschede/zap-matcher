import { Component } from '@angular/core';
import {Store} from '@ngrx/store';
import {ApplicationState} from '../store/application-state';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private store: Store<ApplicationState>) {

    store.subscribe(state => console.log('New state:', state));
  }
}
