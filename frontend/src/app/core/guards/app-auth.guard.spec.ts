import { TestBed } from '@angular/core/testing';

import { KeycloakAngularModule } from 'keycloak-angular';
import { AppAuthGuard } from './app-auth.guard';
import { RouterTestingModule } from '@angular/router/testing';
import { Component } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

describe('AppAuthGuard', () => {
  let guard: AppAuthGuard;
  const state = {} as RouterStateSnapshot;
  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([]),
        KeycloakAngularModule,
      ]
    });
    guard = TestBed.inject(AppAuthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  function guardForUserWithRoles(requiredRoles?: string[]): AppAuthGuard {
    if (requiredRoles) {
      guard['authenticated'] = true;
      guard['roles'] = requiredRoles;
    }
    return guard;
  }

  function forRoute(roles?: string[]): ActivatedRouteSnapshot {
    const route = {} as ActivatedRouteSnapshot;
    route.data = { roles };
    return route;
  }

  describe('isAccessAllowed', () => {
    it('should return false when no token provided', async done => {
      await guardForUserWithRoles().isAccessAllowed(forRoute(), state)
        .then(authorized => {
          expect(authorized).toBeFalsy();
          done();
        });
    });
    it('should return true when no roles are required', async done => {
      const route = {} as ActivatedRouteSnapshot;
      route.data = { role: [] };
      await guardForUserWithRoles([]).isAccessAllowed(forRoute([]), state)
        .then(authorized => {
          expect(authorized).toBeTruthy();
          done();
        });
    });
    it('should return false when no roles are provided', async done => {
      await guardForUserWithRoles(['user']).isAccessAllowed(forRoute(['admin']), state)
        .then(authorized => {
          expect(authorized).toBeFalsy();
          done();
        });
    });
    it('should return true when roles are provided', async done => {
      await guardForUserWithRoles(['user']).isAccessAllowed(forRoute(['user']), state)
        .then(authorized => {
          expect(authorized).toBeTruthy();
          done();
        });
    });

    it('should return true when not all roles are provided', async done => {
      await guardForUserWithRoles(['user', 'admin']).isAccessAllowed(forRoute(['user']), state)
        .then(authorized => {
          expect(authorized).toBeTruthy();
          done();
        });
    });
  });
});
