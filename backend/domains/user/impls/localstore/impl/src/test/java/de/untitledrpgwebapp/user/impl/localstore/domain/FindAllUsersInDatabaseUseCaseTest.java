package de.untitledrpgwebapp.user.impl.localstore.domain;

import static de.untitledrpgwebapp.user.testfixture.UserFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_RESPONSES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for FindAllUsersInDatabaseUseCase unit.")
class FindAllUsersInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with the expected parameter and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryAndMapperWithExpectedParameters() {
    // GIVEN
    PageAndSortConfig config = mock(PageAndSortConfig.class);
    FindAllUsersRequest request =
        FindAllUsersRequest.builder().config(config).correlationId(CORRELATION_ID).build();
    UserDao dao = mock(UserDao.class);
    when(dao.findAll(any())).thenReturn(USER_RESPONSES);

    // WHEN
    Collection<UserResponse> actual =
        new FindAllUsersInDatabaseUseCase(dao).execute(request);

    // THEN
    assertThat(actual, hasSize(USER_RESPONSES.size()));
    for(UserResponse response : actual) {
      assertThat(response.getCorrelationId(), is(CORRELATION_ID));
    }

    verify(dao).findAll(config);
  }
}