package de.untitledrpgwebapp.user.impl.localstore.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindAllUsersInDatabaseUseCase unit.")
class FindAllUsersInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with the expected parameter and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN
    UUID correlationId = UUID.randomUUID();
    FindAllUsersRequest request =
        FindAllUsersRequest.builder().correlationId(correlationId).build();

    UserResponse responseOne = UserResponse.builder().build();
    UserResponse responseTwo = UserResponse.builder().build();
    List<UserResponse> responses = List.of(responseOne, responseTwo);
    UserRepository repository = mock(UserRepository.class);
    when(repository.findAll()).thenReturn(responses);

    // WHEN
    Collection<UserResponse> actual =
        new FindAllUsersInDatabaseUseCase(repository).execute(request);

    // THEN
    assertThat(actual, hasSize(responses.size()));
    for(UserResponse response : actual) {
      assertThat(response.getCorrelationId(), is(correlationId));
    }

    verify(repository).findAll();
  }
}