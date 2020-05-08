import { createSelector } from '@ngrx/store';
import { userAdapter } from './user.reducer';

const selectFeature = (state: any) => state.user;

export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = userAdapter.getSelectors(selectFeature);

export const selectCurrentUserId = createSelector(
  selectFeature,
  user => user.me
);

export const selectCurrentUser = createSelector(
  selectEntities,
  selectCurrentUserId,
  (userEntities, userId) => userEntities[userId]
);
