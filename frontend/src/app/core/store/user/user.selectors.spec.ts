import * as UserSelectors from './user.selectors';

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
