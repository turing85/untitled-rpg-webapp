package de.untitledrpgwebapp.domain.user.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.boundary.user.UserRepository;
import de.untitledrpgwebapp.boundary.user.mapper.UserMapper;
import de.untitledrpgwebapp.boundary.user.request.FindAllUsersRequest;
import de.untitledrpgwebapp.boundary.user.response.UserBuilder;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindAllUsersFromDatabaseUseCase unit.")
class FindAllUsersFromDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository and the mapper with the expected parameters")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN
    UUID correlationId = UUID.randomUUID();
    UserResponseBuilder userResponseBuilder = mock(UserResponseBuilder.class);
    FindAllUsersRequest request = mock(FindAllUsersRequest.class);
    when(request.getCorrelationId()).thenReturn(correlationId);
    when(request.createUserResponseBuilder()).thenReturn(userResponseBuilder);

    List<UserBuilder> foundUsers = Collections.emptyList();
    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.findAll(any(FindAllUsersRequest.class))).thenReturn(foundUsers);
    List<UserResponseBuilder> expected = Collections.emptyList();
    UserMapper userMapper = mock(UserMapper.class);
    when(userMapper.requestsToRequests(anyCollection(), any())).thenReturn(expected);

    // WHEN
    Collection<UserResponseBuilder> actual =
        new FindAllUsersFromDatabaseUseCase(userRepository, userMapper).execute(request);

    // THEN
    assertSame(expected, actual);

    verify(userRepository).findAll(same(request));
    verify(userMapper).requestsToRequests(same(foundUsers), any());
  }
}