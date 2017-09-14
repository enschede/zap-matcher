import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Http, Response} from '@angular/http';



@Injectable()
export class ProfileService {

  constructor(private http: Http) {}

  getProfile(): Observable<any> {
    return this.http.get(environment.apiUrl + '/user/profile', { withCredentials: true })
      .map((res: Response) => res.json());
  }

}
