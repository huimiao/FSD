import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {NGXLogger} from 'ngx-logger';
import {map} from 'rxjs/operators';
import {NewUser} from '../model/new-user';
import { environment } from '../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json;charset=UTF-8'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient,
              private logger: NGXLogger) {
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${environment.gatewayurl}/api/auth/login`,
      {username, password},
      {headers: httpOptions.headers, observe: 'response' as 'body'})
      .pipe(map(resp => {
        localStorage.setItem('current_user', resp.body.current_user);
        localStorage.setItem('current_user_role', resp.body.current_user_role);
        localStorage.setItem('token', resp.body.token);
        return resp.body.current_user;
      }));
  }

  logout() {
    localStorage.clear();
  }

  signup(newUser: NewUser) {
    return this.http.post<any>(`${environment.gatewayurl}/api/account/v1/signup`,
      newUser,
      {headers: httpOptions.headers});
  }
}
