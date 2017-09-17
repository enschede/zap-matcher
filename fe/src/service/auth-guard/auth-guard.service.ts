
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Store} from '@ngrx/store';
import {ApplicationState} from '../../store/application-state';
import {Injectable} from '@angular/core';

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(private router: Router, private store: Store<ApplicationState>) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.store
      .select((appState) => appState.account.isAuthenticated)
      .do((val) => this.redirectIfFalse(val));
  }

  redirectIfFalse(value: boolean) {
    if (!value) {
      this.router.navigate(['/login']);
    }
  }
}
