package de.untitledrpgwebapp.user.bean.proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindAllUsersInDatabaseUseCase;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindAllUsersUseCaseProxy unit.")
class FindAllUsersUseCaseProxyTest {

  @Test
  @DisplayName("Should create a FindAllUsersUseCaseProxy with the expected DAO.")
  void shouldCreateExpectedFindAllUsersUseCase() {
    // GIVEN
    UserDao dao = mock(UserDao.class);

    // WHEN
    FindAllUsersUseCaseProxy actual = new FindAllUsersUseCaseProxy(dao);

    // THEN
    assertThat(actual.getDao(), sameInstance(dao));
  }
}