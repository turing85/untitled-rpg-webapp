package de.untitledrpgwebapp.user.impl.quarkus.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.oauth2.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.CreateUserInDatabaseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateUserUseCaseBean unit")
class CreateUserUseCaseBeanTest {

  @Test
  @DisplayName("Should create a CreateUserInDatabaseUseCase with the expected settings")
  void shouldCreateExpectedCreateLanguageUseCase() {
    // GIVEN
    UserRepository repository = mock(UserRepository.class);
    CreateAccountUseCase createAccount = mock(CreateAccountUseCase.class);
    FindLanguageByTagUseCase findLanguage = mock(FindLanguageByTagUseCase.class);

    // WHEN
    CreateUserUseCase created =
        new CreateUserUseCaseBean().createUser(repository, createAccount, findLanguage);

    // THEN
    assertThat(created, instanceOf(CreateUserInDatabaseUseCase.class));
    CreateUserInDatabaseUseCase actual = (CreateUserInDatabaseUseCase) created;
    assertThat(actual.getRepository(), sameInstance(repository));
    assertThat(actual.getCreateAccount(), sameInstance(createAccount));
    assertThat(actual.getFindLanguage(), sameInstance(findLanguage));
  }
}