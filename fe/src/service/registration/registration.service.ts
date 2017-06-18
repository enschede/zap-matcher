import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {UserRegisterCommand} from '../../command/index';
import {environment} from '../../environments/environment';


@Injectable()
export class RegistrationService {

  constructor(private http: Http) {}

  register(command: UserRegisterCommand): Observable<any> {
    console.log(command);
    return this.http.post(environment.apiUrl + '/public/createUser', command)
      .map((res: Response) => res.json());

  }

}
