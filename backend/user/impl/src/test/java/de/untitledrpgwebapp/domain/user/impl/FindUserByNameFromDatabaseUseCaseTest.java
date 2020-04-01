package de.untitledrpgwebapp.domain.user.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.boundary.user.UserRepository;
import de.untitledrpgwebapp.boundary.user.mapper.UserMapper;
import de.untitledrpgwebapp.boundary.user.request.FindUserByNameRequest;
import de.untitledrpgwebapp.boundary.user.response.UserBuilder;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindUserByNameFromDatabaseUseCaseTest unit.")
class FindUserByNameFromDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository and the mapper with the expected parameters")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN
    UUID correlationId = UUID.randomUUID();
    String name = "name";
    UserResponseBuilder userResponseBuilder = mock(UserResponseBuilder.class);
    FindUserByNameRequest request = mock(FindUserByNameRequest.class);
    when(request.getCorrelationId()).thenReturn(correlationId);
    when(request.getName()).thenReturn(name);
    when(request.getUserResponseBuilder()).thenReturn(userResponseBuilder);

    UserBuilder found = mock(UserBuilder.class);
    when(found.setCorrelationId(any(UUID.class))).thenReturn(found);
    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.findByName(any())).thenReturn(Optional.of(found));

    UserResponseBuilder expected = mock(UserResponseBuilder.class);

    UserMapper userMapper = mock(UserMapper.class);
    when(userMapper.requestToRequest(any(UserBuilder.class), any())).thenReturn(expected);

    // WHEN
    Optional<UserResponseBuilder> fetched =
        new FindUserByNameFromDatabaseUseCase(userRepository, userMapper).execute(request);

    // THEN
    assertTrue(fetched.isPresent());
    UserResponseBuilder actual = fetched.get();
    assertSame(expected, actual);

    verify(userRepository).findByName(same(request));
    verify(userMapper).requestToRequest(same(found), same(userResponseBuilder));
  }

}