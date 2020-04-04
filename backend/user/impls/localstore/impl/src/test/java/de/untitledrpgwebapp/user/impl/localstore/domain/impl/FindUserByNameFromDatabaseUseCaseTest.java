package de.untitledrpgwebapp.user.impl.localstore.domain.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.impl.localstore.boundary.UserRepository;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.impl.localstore.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserEntity;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserResponse;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindUserByNameFromDatabaseUseCaseTest unit.")
class FindUserByNameFromDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository and the mapper with the expected parameters")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN:
    UUID correlationId = UUID.randomUUID();
    FindUserByNameRequest request = FindUserByNameRequest.builder()
        .correlationId(correlationId)
        .build();

    UserEntity entity = UserEntity.builder().build();
    UserRepository repository = mock(UserRepository.class);
    when(repository.findByName(any())).thenReturn(Optional.of(entity));

    UserResponse response = UserResponse.builder()
        .correlationId(correlationId)
        .build();

    UserMapper mapper = mock(UserMapper.class);
    when(mapper.entityToRequest(any())).thenReturn(response);

    // WHEN
    Optional<UserResponse> result =
        new FindUserByNameFromDatabaseUseCase(repository, mapper).execute(request);

    // THEN
    assertTrue(result.isPresent());
    UserResponse actual = result.get();
    assertEquals(correlationId, actual.getCorrelationId());

    verify(repository).findByName(same(request));
    verify(mapper).entityToRequest(same(entity));
  }
}