package de.untitledrpgwebapp.user.impl.quarkus.boundary.endpoint;

import static de.untitledrpgwebapp.user.impl.quarkus.boundary.testfixture.UserDtoFixture.USER_DTO_ONE;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.mapper.UserMapper;
import io.quarkus.security.identity.SecurityIdentity;
import java.security.Principal;
import java.util.Optional;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MeEndpointTest {

  private FindUserByNameUseCase findUser = mock(FindUserByNameUseCase.class);
  private UserMapper mapper = mock(UserMapper.class);
  private final SecurityIdentity identity = mock(SecurityIdentity.class);
  private final MeEndpoint uut = new MeEndpoint(findUser, identity, mapper);

  @Test
  @DisplayName("Should call findUser with the expected parameters and return the expected response "
      + "when getMe is called.")
  void shouldCallFindUserWithExpectedParametersAndReturnExpectedResultWhenGetMeIsCalled() {
    // GIVEN
    when(findUser.execute(any()))
        .thenReturn(Optional.of(UserResponse.builder().name(USER_ONE_NAME).build()));
    when(mapper.responseToDto(any())).thenReturn(USER_DTO_ONE);
    Principal principal = mock(Principal.class);
    when(principal.getName()).thenReturn(USER_ONE_NAME);
    when(identity.getPrincipal()).thenReturn(principal);

    // WHEN
    Response actual = uut.findCurrentUser(CORRELATION_ID);

    // THEN
    UserResponseValidator.assertResponseIsAsExpected(actual);

    verify(findUser).execute(argThat(request -> {
      assertThat(request.getName(), is(USER_ONE_NAME));
      assertThat(request.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
  }
}