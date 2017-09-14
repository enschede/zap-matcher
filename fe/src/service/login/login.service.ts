import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {LoginStartedPayload} from '../../store/account/account-actions';
import {commonHttpHeaders} from '../common-http-headers';




@Injectable()
export class LoginService {

  constructor(private http: Http) {}

  login(command: LoginStartedPayload): Observable<any> {
    return this.http.post(environment.apiUrl + '/public/login', command, commonHttpHeaders())
      .map((res: Response) => res.json());
  }

}
