package de.untitledrpgwebapp.user.impl.quarkus.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.FindUserByNameInDatabaseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindUserByNameUseCaseBean unit")
class FindUserByNameUseCaseBeanTest {

  @Test
  @DisplayName("Should create a FindUserByNameInDatabaseUseCase with the expected repository.")
  void shouldCreateExpectedFindUserByNameUseCase() {
    // GIVEN
    UserRepository repository = mock(UserRepository.class);

    // WHEN
    FindUserByNameUseCase created = new FindUserByNameUseCaseBean().findUser(repository);

    // THEN
    assertThat(created, instanceOf(FindUserByNameInDatabaseUseCase.class));
    FindUserByNameInDatabaseUseCase actual = (FindUserByNameInDatabaseUseCase) created;
    assertThat(actual.getRepository(), sameInstance(repository));
  }

}