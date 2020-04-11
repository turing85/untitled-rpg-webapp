import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { User } from 'src/app/model/user/user.keycloak.model';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { fromKeycloakProfile } from 'src/app/model/user/user.keycload.mapper';
import { Router } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import { HttpClient } from '@angular/common/http';


type LanguageTag = 'en-GB' | 'de-DE';

const USERS_URL = "http://localhost:8081/users"

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(
    private keycloakAngular: KeycloakService,
    private platformLocation: PlatformLocation,
    private httpService: HttpClient
  ) { }

  getUserDetails(): Observable<User> {
    const userDetails = this.keycloakAngular.getKeycloakInstance().profile;
    return of(userDetails).pipe(map(fromKeycloakProfile));
  }

  /**
   * Logout the user and redirect to landing page
   */
  logout() {
    const landingPageUrl = (this.platformLocation as any).location.origin;
    this.keycloakAngular.logout(landingPageUrl);
  }

  register(name: string, email: string, password: string, preferredLanguageTag: LanguageTag): Observable<unknown> {
    return this.httpService.post(USERS_URL, { name, email, password, preferredLanguageTag });
  }
}
