import { createSelector } from '@ngrx/store';
import { UserState } from './user.reducer';
 
 
export const selectFeature = (state: any) => state.user;
 
export const selectMe = createSelector(
  selectFeature,
  (state: UserState) => {console.log(state);return state.users[state.me]}
);