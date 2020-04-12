package de.untitledrpgwebapp.user.impl.localstore.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindUserByNameInDatabaseUseCase unit.")
class FindUserByNameInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with the expected parameter and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN:
    UUID correlationId = UUID.randomUUID();
    final String name = "name";
    FindUserByNameRequest request = FindUserByNameRequest.builder()
        .name(name)
        .correlationId(correlationId)
        .build();
    UserResponse response = UserResponse.builder().build();

    UserRepository repository = mock(UserRepository.class);
    when(repository.findByName(any())).thenReturn(Optional.of(response));
    
    // WHEN
    Optional<UserResponse> result =
        new FindUserByNameInDatabaseUseCase(repository).execute(request);

    // THEN
    assertTrue(result.isPresent());
    assertThat(result.get().getCorrelationId(), is(correlationId));

    verify(repository).findByName(name);
  }
}