import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { User } from 'src/app/model/user/user.keycloak.model';
import { Observable, of } from 'rxjs';
import { PlatformLocation } from '@angular/common';
import { HttpClient } from '@angular/common/http';


export type LanguageTag = 'en-GB' | 'de-DE';

const USERS_URL = 'http://localhost:8081/users';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(
    private keycloakAngular: KeycloakService,
    private platformLocation: PlatformLocation,
    private httpService: HttpClient
  ) { }

  /**
   * Logout the user and redirect to landing page
   */
  logout() {
    const landingPageUrl = (this.platformLocation as any).location.origin;
    this.keycloakAngular.logout(landingPageUrl);
  }

  register(name: string, email: string, password: string, preferredLanguageTag: LanguageTag): Observable<User> {
    return this.httpService.post<User>(USERS_URL, { name, email, password, preferredLanguageTag });
  }

  getMe(): Observable<User> {
    const userDetails = this.keycloakAngular.getKeycloakInstance().profile;
    return this.httpService.get<User>(`${USERS_URL}/${userDetails.username}`);
  }

  getUser(name: string): Observable<User> {
    return this.httpService.get<User>(`${USERS_URL}/${name}`);
  }

  getUsers(): Observable<User[]> {
    return this.httpService.get<User[]>(`${USERS_URL}`);
  }
}
