package de.untitledrpgwebapp.user.impl.quarkus.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindUserByEmailUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.FindUserByEmailInDatabaseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindUserByEmailUseCaseBean unit.")
class FindUserByEmailUseCaseBeanTest {

  @Test
  @DisplayName("Should create a FindUserByEmailUseCaseBean with the expected settings.")
  void shouldCreateExpectedFindUserByEmailUseCaseBean() {
    // GIVEN
    UserDao dao = mock(UserDao.class);

    // WHEN
    FindUserByEmailUseCase created = new FindUserByEmailUseCaseBean().findByEmail(dao);

    // THEN
    assertThat(created, instanceOf(FindUserByEmailUseCase.class));
    FindUserByEmailInDatabaseUseCase actual = (FindUserByEmailInDatabaseUseCase) created;
    assertThat(actual.getDao(), sameInstance(dao));
  }

}