import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { CreateMatch } from '@app/shared/match/match.interface';

const root = '/api/matches';

@Injectable()
export class MatchService {
  constructor(private httpClient: HttpClient) {}

  join(id: Number) {
    return this.httpClient.patch(`${root}/${id}`, {});
  }

  query(params?: {}) {
    return this.httpClient.get(root, { params });
  }

  get(id: Number, params?: { string: string }): Observable<Object> {
    return this.httpClient.get(root + '/' + id, { params });
  }

  save(data: CreateMatch) {
    return this.httpClient.post(root, data);
  }

  update(data: any) {
    return this.httpClient.put(root + '/' + data.id, data);
  }

  remove(data: any) {
    return this.httpClient.delete(root + '/' + data.id);
  }

  getRecommendedCards() {
    return this.httpClient.get(`/api/cards/recommended`);
  }
}
