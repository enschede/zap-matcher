import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {RegistrationStartedPayload} from '../../store/registration/registration-actions';


@Injectable()
export class RegistrationService {

  constructor(private http: Http) {}

  register(command: RegistrationStartedPayload): Observable<any> {
    return this.http.post(environment.apiUrl + '/public/createUser', command)
      .map((res: Response) => res.json());
  }

}
