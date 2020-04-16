package de.untitledrpgwebapp.user.impl.quarkus.beans;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.FindAllUsersInDatabaseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateLanguageUseCaseBean unit.")
class FindAllUsersUseCaseBeanTest {

  @Test
  @DisplayName("Should create a FindAllUsersInDatabaseUseCase with the expected DAO.")
  void shouldCreateExpectedFindAllUsersUseCase() {
    // GIVEN
    UserDao dao = mock(UserDao.class);

    // WHEN
    FindAllUsersUseCase created = new FindAllUsersUseCaseBean().findAllUsers(dao);

    // THEN
    assertThat(created, instanceOf(FindAllUsersInDatabaseUseCase.class));
    FindAllUsersInDatabaseUseCase actual = (FindAllUsersInDatabaseUseCase) created;
    assertThat(actual.getDao(), sameInstance(dao));
  }
}
