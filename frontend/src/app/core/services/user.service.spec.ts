import { TestBed } from '@angular/core/testing';

import { instance, mock, verify, when, anything } from 'ts-mockito';
import { UserService } from './user.service';
import { KeycloakService } from 'keycloak-angular';
import { HttpClient } from '@angular/common/http';
import { PlatformLocation } from '@angular/common';
import { User } from 'src/app/model/user/user.keycloak.model';
import { of } from 'rxjs';

describe('UserService', () => {
  const USERS_URL = 'http://localhost:8081/users';

  let service: UserService;
  const keycloakServiceMock = mock(KeycloakService);
  const httpClientMock = mock(HttpClient);

  const data = { name: 'a', email: 'a@b', password: 'pw', preferredLanguageTag: 'de-DE' };
  when(httpClientMock.post<User>(USERS_URL, anything())).thenCall((url, a) => of({ ...a } as unknown as User));
  when(httpClientMock.get<User>(`${USERS_URL}/a`)).thenCall((url, a) => of(data  as unknown as User));
  when(httpClientMock.get<User>(`${USERS_URL}`)).thenCall((url, a) => of([data, {email: 'u'}] as unknown as User[]));
  const o = {profile: {username: 'a'}};
  when(keycloakServiceMock.getKeycloakInstance()).thenReturn(o as any);
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {
          provide: KeycloakService,
          useValue: instance(keycloakServiceMock)
        },
        {
          provide: HttpClient,
          useValue: instance(httpClientMock)
        }
      ]

    });
    service = TestBed.inject(UserService);
    /* tslint:disable */
    service['platformLocation'] = { location: { origin: 'foo' } } as unknown as PlatformLocation;
      /* tslint:enable */
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call keycloak when logout', () => {
    service.logout();
    verify(keycloakServiceMock.logout('foo')).once();
  });

  it('should post to user service when register', done => {
    service.register('a', 'a@b', 'pw', 'de-DE').subscribe(result => {
      expect(result.name).toEqual('a');
      done();
    });
  });

  it('should get user by name', done => {
    service.getUser('a').subscribe(result => {
      expect(result.name).toEqual('a');
      done();
    });
  });

  it('should get all user', done => {
    service.getUsers().subscribe(result => {
      expect(result.length).toEqual(2);
      done();
    });
  });
  it('should get me', done => {
    service.getMe().subscribe(result => {
      expect(result.name).toEqual('a');
      done();
    });
  });
});
