import { TestBed } from '@angular/core/testing';

import { KeycloakAngularModule } from 'keycloak-angular';
import { AppAuthGuard } from './app-auth.guard';
import { RouterTestingModule } from '@angular/router/testing';
import { Component } from '@angular/core';

describe('AppAuthGuard', () => {
  let guard: AppAuthGuard;

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
});
