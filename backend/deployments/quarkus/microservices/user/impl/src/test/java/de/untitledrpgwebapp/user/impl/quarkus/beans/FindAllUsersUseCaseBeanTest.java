package de.untitledrpgwebapp.user.impl.quarkus.beans;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.FindAllUsersInDatabaseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateLanguageUseCaseBean unit")
class FindAllUsersUseCaseBeanTest {

  @Test
  @DisplayName("Should create a FindAllUsersInDatabaseUseCase with the expected repository.")
  void shouldCreateExpectedFindAllUsersUseCase() {
    // GIVEN
    UserRepository repository = mock(UserRepository.class);

    // WHEN
    FindAllUsersUseCase created = new FindAllUsersUseCaseBean().findAllUsers(repository);

    // THEN
    assertThat(created, instanceOf(FindAllUsersInDatabaseUseCase.class));
    FindAllUsersInDatabaseUseCase actual = (FindAllUsersInDatabaseUseCase) created;
    assertThat(actual.getRepository(), sameInstance(repository));
  }
}
