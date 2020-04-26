import { createSelector } from '@ngrx/store';
import { UserState, userAdapter } from './user.reducer';

export const selectFeature = (state: any) => state.user;

export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = userAdapter.getSelectors(selectFeature);

export const selectMe = createSelector(selectFeature, (state: UserState) => state.me, (state, name) => null
);

export const selectCurrentUserId = createSelector(
  selectFeature,
  user => user.me
);

export const selectCurrentUser = createSelector(
  selectEntities,
  selectCurrentUserId,
  (userEntities, userId) => userEntities[userId]
);
