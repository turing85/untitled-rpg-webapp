package de.untitledrpgwebapp.user.bean.proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.oidc.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateUserUseCaseProxy unit.")
class CreateUserUseCaseProxyTest {

  @Test
  @DisplayName("Should create a CreateUserUseCaseProxy with the expected settings.")
  void shouldCreateExpectedCreateUserUseCase() {
    // GIVEN
    UserDao dao = mock(UserDao.class);
    FindLanguageByTagUseCase findLanguage = mock(FindLanguageByTagUseCase.class);
    CreateAccountUseCase createAccount = mock(CreateAccountUseCase.class);

    // WHEN
    CreateUserUseCaseProxy actual = new CreateUserUseCaseProxy(
        dao,
        findLanguage,
        createAccount);

    // THEN
    assertThat(actual.getDao(), sameInstance(dao));
    assertThat(actual.getFindLanguage(), sameInstance(findLanguage));
    assertThat(actual.getCreateAccount(), sameInstance(createAccount));
  }
}