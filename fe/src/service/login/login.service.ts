import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {LoginStartedPayload} from '../../store/account/account-actions';




@Injectable()
export class LoginService {

  constructor(private http: Http) {}

  login(command: LoginStartedPayload): Observable<any> {
    return this.http.post(environment.apiUrl + '/public/login', command)
      .map((res: Response) => res.json());
  }

}
