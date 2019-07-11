import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PlayItem } from './playItem.model';
import { Observable } from 'rxjs';

const PROTOCOL = 'http';
const PORT = 3500;

@Injectable()
export class RestDataSource {
  baseUrl: string;
  constructor(private httpClient: HttpClient) {
    this.baseUrl = `${PROTOCOL}://${location.hostname}:${PORT}/`;
  }

  getPlayList(): Observable<PlayItem[]> {
    return this.httpClient.get<PlayItem[]>(this.baseUrl + 'youtube');
  }

  deletePlayItemById(id: string): Observable<Object> {
    return this.httpClient.delete(this.baseUrl + 'youtube/' + id);
  }

  updatePlayItem(id: string, item: any): Observable<Object> {
    return this.httpClient.patch(this.baseUrl + 'youtube/' + id, item);
  }

  savePlayItem(item: PlayItem): Observable<Object> {
    return this.httpClient.post(this.baseUrl + 'youtube/', item);
  }
}
