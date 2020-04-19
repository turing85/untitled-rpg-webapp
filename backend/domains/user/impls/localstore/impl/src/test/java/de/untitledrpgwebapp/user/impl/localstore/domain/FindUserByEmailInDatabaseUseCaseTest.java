package de.untitledrpgwebapp.user.impl.localstore.domain;

import static de.untitledrpgwebapp.user.testfixture.UserFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_EMAIL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.request.FindUserByEmailRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindUserByEmailInDatabaseUseCaseTest unit.")
class FindUserByEmailInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the DAO with the expected parameter and return the expected response "
      + "when everything is ok.")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN:
    FindUserByEmailRequest request = FindUserByEmailRequest.builder()
        .email(USER_ONE_EMAIL)
        .correlationId(CORRELATION_ID)
        .build();
    UserResponse response = UserResponse.builder().build();

    UserDao dao = mock(UserDao.class);
    when(dao.findByEmail(any())).thenReturn(Optional.of(response));

    // WHEN
    Optional<UserResponse> result =
        new FindUserByEmailInDatabaseUseCase(dao).execute(request);

    // THEN
    assertTrue(result.isPresent());
    assertThat(result.get().getCorrelationId(), is(CORRELATION_ID));

    verify(dao).findByEmail(USER_ONE_EMAIL);
  }
}