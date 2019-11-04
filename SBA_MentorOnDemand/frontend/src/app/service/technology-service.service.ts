import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {NGXLogger} from 'ngx-logger';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json;charset=UTF-8'
  })
};

@Injectable({
  providedIn: 'root'
})
export class TechnologyService {

  constructor(private http: HttpClient,
              private logger: NGXLogger) {
  }

  getAllTechnologies() {
    return this.http.get<any>(`${environment.gatewayurl}/api/technology/v1/technologies`);
  }
}
