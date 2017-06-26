import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {UserLoginCommand} from '../../command/index';



@Injectable()
export class LoginService {

  constructor(private http: Http) {}

  login(command: UserLoginCommand): Observable<any> {
    return this.http.post(environment.apiUrl + '/public/login', command)
      .map((res: Response) => res.json());
  }

}
