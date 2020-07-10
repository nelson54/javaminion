import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Credentials, CredentialsService } from './credentials.service';
import { catchError, map } from 'rxjs/operators';

export interface LoginContext {
  username: string;
  password: string;
  remember?: boolean;
}

const routes = {
  authentication: () => '/api/authentication'
};

/**
 * Provides a base for authentication workflow.
 * The login/logout methods should be replaced with proper implementation.
 */
@Injectable()
export class AuthenticationService {
  constructor(private httpClient: HttpClient, private credentialsService: CredentialsService) {}

  /**
   * Authenticates the user.
   * @param context The login parameters.
   * @return The user credentials.
   */
  login(context: LoginContext): Observable<Credentials> {
    // Replace by proper authentication call
    const data: Credentials = {
      id: null,
      username: context.username,
      token: null
    };

    return this.httpClient.post(routes.authentication(), context).pipe(
      map((body: any) => {
        data.id = body.account.id;
        data.token = body.token;
        this.credentialsService.setCredentials(data, context.remember);
        return data;
      }),
      catchError(() => of(data))
    );
  }

  register(username: String, password: String) {}

  /**
   * Logs out the user and clear credentials.
   * @return True if the user was logged out successfully.
   */
  logout(): Observable<boolean> {
    // Customize credentials invalidation here
    this.credentialsService.setCredentials();
    return of(true);
  }
}
