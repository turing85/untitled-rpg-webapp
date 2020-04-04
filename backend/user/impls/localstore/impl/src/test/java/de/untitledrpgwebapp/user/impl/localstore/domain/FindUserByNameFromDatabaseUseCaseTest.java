package de.untitledrpgwebapp.user.impl.localstore.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserEntity;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindUserByNameFromDatabaseUseCaseTest unit.")
class FindUserByNameFromDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository and the mapper with the expected parameters and return"
      + " the expected response when everything is ok.")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN:
    UUID correlationId = UUID.randomUUID();
    FindUserByNameRequest request = FindUserByNameRequest.builder()
        .correlationId(correlationId)
        .build();

    UserEntity entity = UserEntity.builder().build();
    UserRepository repository = mock(UserRepository.class);
    when(repository.findByName(any())).thenReturn(Optional.of(entity));

    UserResponse response = UserResponse.builder().build();

    UserMapper mapper = mock(UserMapper.class);
    when(mapper.entityToResponse(any())).thenReturn(response);

    // WHEN
    Optional<UserResponse> result =
        new FindUserByNameFromDatabaseUseCase(repository, mapper).execute(request);

    // THEN
    assertTrue(result.isPresent());
    assertEquals(correlationId, result.get().getCorrelationId());

    verify(repository).findByName(request);
    verify(mapper).entityToResponse(entity);
  }
}