package de.untitledrpgwebapp.user.impl.localstore.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.boundary.response.UserEntity;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindAllUsersFromDatabaseUseCase unit.")
class FindAllUsersFromDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository and the mapper with the expected parameters and return"
      + " the expected response when everything is ok.")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN
    UUID correlationId = UUID.randomUUID();
    FindAllUsersRequest request =
        FindAllUsersRequest.builder().correlationId(correlationId).build();

    List<UserEntity> foundUsers = Collections.emptyList();
    UserRepository repository = mock(UserRepository.class);
    when(repository.findAll(any())).thenReturn(foundUsers);
    UserResponse responseOne = UserResponse.builder().build();
    UserResponse responseTwo = UserResponse.builder().build();
    List<UserResponse> responses = List.of(responseOne, responseTwo);
    UserMapper mapper = mock(UserMapper.class);
    when(mapper.entitiesToRequests(anyCollection())).thenReturn(responses);

    // WHEN
    Collection<UserResponse> actual =
        new FindAllUsersFromDatabaseUseCase(repository, mapper).execute(request);

    // THEN
    assertThat(actual, hasSize(responses.size()));
    for(UserResponse response : actual) {
      assertEquals(correlationId, response.getCorrelationId());
    }

    verify(repository).findAll(request);
    verify(mapper).entitiesToRequests(foundUsers);
  }
}