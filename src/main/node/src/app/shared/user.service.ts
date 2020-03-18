import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

@Injectable()
export class UserService {
  private apiRoot = '/api/accounts';

  constructor(private httpClient: HttpClient) {}

  getUser() {
    return this.httpClient.get('/api/account');
  }

  getUsers() {
    return this.httpClient.get(this.apiRoot);
  }

  register(user: any) {
    return this.httpClient.post('/api/register', user);
  }

  query(params: { string: string }) {
    return this.httpClient.get(this.apiRoot, { params });
  }

  get(id: string, params?: { string: string }) {
    return this.httpClient.get('/api/players/' + id, { params });
  }

  save(data: any) {
    return this.httpClient.post(this.apiRoot, data);
  }

  update(data: any) {
    return this.httpClient.put(this.apiRoot + '/' + data.id, data);
  }

  remove(data: any) {
    return this.httpClient.delete(this.apiRoot + '/' + data.id);
  }
}
