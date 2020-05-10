import * as UserSelectors from './user.selectors';
import * as UserActions from './user.actions';
import { TestBed } from '@angular/core/testing';
import { userReducer } from './user.reducer';
import { User } from 'src/app/model/user/user.keycloak.model';
import { combineReducers, select } from '@ngrx/store';

describe('UserSelectors', () => {


  it('should selectMe from store', () => {
    const state = { me: 'a1' };
    expect(UserSelectors.selectCurrentUserId.projector(state)).toBe('a1');
  });

  it('should selectMe from store', () => {
    const e = { a: 'a1', b: 'b2' };
    const id = 'a';
    expect(UserSelectors.selectCurrentUser.projector(e, id)).toBe('a1');
  });
});
