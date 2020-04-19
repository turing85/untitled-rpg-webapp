package de.untitledrpgwebapp.user.impl.quarkus.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.oidc.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByEmailUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.CreateUserInDatabaseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateUserUseCaseBean unit.")
class CreateUserUseCaseBeanTest {

  @Test
  @DisplayName("Should create a CreateUserInDatabaseUseCase with the expected settings.")
  void shouldCreateExpectedCreateLanguageUseCase() {
    // GIVEN
    UserDao dao = mock(UserDao.class);
    FindUserByNameUseCase findByName = mock(FindUserByNameUseCase.class);
    FindUserByEmailUseCase findByEmail = mock(FindUserByEmailUseCase.class);
    FindLanguageByTagUseCase findLanguage = mock(FindLanguageByTagUseCase.class);
    CreateAccountUseCase createAccount = mock(CreateAccountUseCase.class);

    // WHEN
    CreateUserUseCase created = new CreateUserUseCaseBean().createUser(
        dao,
        findByName,
        findByEmail,
        findLanguage,
        createAccount);

    // THEN
    assertThat(created, instanceOf(CreateUserInDatabaseUseCase.class));
    CreateUserInDatabaseUseCase actual = (CreateUserInDatabaseUseCase) created;
    assertThat(actual.getDao(), sameInstance(dao));
    assertThat(actual.getFindByName(), sameInstance(findByName));
    assertThat(actual.getFindByEmail(), sameInstance(findByEmail));
    assertThat(actual.getFindLanguage(), sameInstance(findLanguage));
    assertThat(actual.getCreateAccount(), sameInstance(createAccount));
  }
}