import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {RegistrationCommand} from '../../store/account/account-commands';


@Injectable()
export class RegistrationService {

  constructor(private http: Http) {}

  register(command: RegistrationCommand): Observable<any> {
    return this.http.post(environment.apiUrl + '/public/createUser', command)
      .map((res: Response) => res.json());
  }

}
