package de.untitledrpgwebapp.user.bean.proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindUserByNameInDatabaseUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindUserByNameUseCaseProxy unit.")
class FindUserByNameUseCaseProxyTest {

  @Test
  @DisplayName("Should create a FindUserByNameUseCaseProxy with the expected DAO.")
  void shouldCreateExpectedFindUserByNameUseCase() {
    // GIVEN
    UserDao dao = mock(UserDao.class);

    // WHEN
    FindUserByNameUseCaseProxy actual = new FindUserByNameUseCaseProxy(dao);

    // THEN
    assertThat(actual.getDao(), sameInstance(dao));
  }
}