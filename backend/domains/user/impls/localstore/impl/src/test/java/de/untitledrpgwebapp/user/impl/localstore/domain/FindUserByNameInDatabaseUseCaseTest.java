package de.untitledrpgwebapp.user.impl.localstore.domain;

import static de.untitledrpgwebapp.user.testfixture.UserFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindUserByNameInDatabaseUseCase unit.")
class FindUserByNameInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with the expected parameter and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN:
    FindUserByNameRequest request = FindUserByNameRequest.builder()
        .name(USER_ONE_NAME)
        .correlationId(CORRELATION_ID)
        .build();
    UserResponse response = UserResponse.builder().build();

    UserDao dao = mock(UserDao.class);
    when(dao.findByName(any())).thenReturn(Optional.of(response));
    
    // WHEN
    Optional<UserResponse> result =
        new FindUserByNameInDatabaseUseCase(dao).execute(request);

    // THEN
    assertTrue(result.isPresent());
    assertThat(result.get().getCorrelationId(), is(CORRELATION_ID));

    verify(dao).findByName(USER_ONE_NAME);
  }
}