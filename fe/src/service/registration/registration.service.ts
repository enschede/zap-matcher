import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {RegistrationStartedPayload} from '../../store/registration/registration-actions';
import {commonHttpHeaders} from "../common-http-headers";


@Injectable()
export class RegistrationService {

  constructor(private http: Http) {}

  register(command: RegistrationStartedPayload): Observable<any> {
    return this.http.post(environment.apiUrl + '/public/createUser', commonHttpHeaders(), command)
      .map((res: Response) => res.json());
  }

}
