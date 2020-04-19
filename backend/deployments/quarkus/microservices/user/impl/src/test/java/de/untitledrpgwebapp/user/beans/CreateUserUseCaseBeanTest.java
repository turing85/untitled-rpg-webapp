package de.untitledrpgwebapp.user.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.oidc.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.CreateUserInDatabaseUseCase;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateUserUseCaseBean unit.")
class CreateUserUseCaseBeanTest {

  @Test
  @DisplayName("Should create a CreateUserInDatabaseUseCase with the expected settings.")
  void shouldCreateExpectedCreateUserUseCase() {
    // GIVEN
    UserDao dao = mock(UserDao.class);
    FindLanguageByTagUseCase findLanguage = mock(FindLanguageByTagUseCase.class);
    CreateAccountUseCase createAccount = mock(CreateAccountUseCase.class);

    // WHEN
    CreateUserUseCase created = new CreateUserUseCaseBean().createUser(
        dao,
        findLanguage,
        createAccount);

    // THEN
    assertThat(created, instanceOf(CreateUserInDatabaseUseCase.class));
    CreateUserInDatabaseUseCase actual = (CreateUserInDatabaseUseCase) created;
    assertThat(actual.getDao(), sameInstance(dao));
    assertThat(actual.getFindLanguage(), sameInstance(findLanguage));
    assertThat(actual.getCreateAccount(), sameInstance(createAccount));
  }
}