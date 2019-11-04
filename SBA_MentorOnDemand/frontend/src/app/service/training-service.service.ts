import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {NGXLogger} from 'ngx-logger';
import {environment} from '../../environments/environment';
import {Training} from '../model/training';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json;charset=UTF-8'
  })
};

@Injectable({
  providedIn: 'root'
})
export class TrainingServiceService {

  constructor(private http: HttpClient,
              private logger: NGXLogger) {
  }

  addNewTraining(newTraining: Training) {
    return this.http.post<any>(`${environment.gatewayurl}/api/training/v1/training`,
      newTraining,
      {headers: httpOptions.headers});
  }

  getALlMentorTrainings() {
    return this.http.get<any>(`${environment.gatewayurl}/api/training/v1/training/all`);
  }

  getAllTrainings(username: string, status: string) {
    return this.http.get<any>(`${environment.gatewayurl}/api/training/v1/training/user?username=${username}&status=${status}`);
  }

  getAllMentorTrainings(username: string, status: string) {
    return this.http.get<any>(`${environment.gatewayurl}/api/training/v1/training/mentor?username=${username}&status=${status}`);
  }

  proposeTraining(id: number, username: string) {
    return this.http.post<any>(`${environment.gatewayurl}/api/training/v1/training/${id}/${username}`,
      null,
      {headers: httpOptions.headers});
  }

  rating(id: number, rating: number, username: string) {
    return this.http.post<any>(`${environment.gatewayurl}/api/training/v1/training/rating/${id}/${rating}?username=${username}`,
      null,
      {headers: httpOptions.headers});
  }
}
