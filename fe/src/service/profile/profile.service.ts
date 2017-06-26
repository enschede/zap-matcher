import {Injectable} from '@angular/core';
import {Http, Response, RequestOptions, Headers} from '@angular/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';



@Injectable()
export class ProfileService {

  constructor(private http: Http) {}

  getProfile(): Observable<any> {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    const options = new RequestOptions({ headers: headers, withCredentials: true });
    return this.http.get(environment.apiUrl + '/user/profile', options)
      .map((res: Response) => res.json());
  }

}
