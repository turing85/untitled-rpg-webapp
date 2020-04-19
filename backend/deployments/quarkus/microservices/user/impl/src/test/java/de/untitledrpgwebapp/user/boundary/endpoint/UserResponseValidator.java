package de.untitledrpgwebapp.user.boundary.endpoint;

import static de.untitledrpgwebapp.user.boundary.testfixture.UserDtoFixture.USER_DTOS;
import static de.untitledrpgwebapp.user.boundary.testfixture.UserDtoFixture.USER_DTO_ONE;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_RESPONSES;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
import de.untitledrpgwebapp.user.boundary.dto.UserDto;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UserResponseValidator {

  public static void assertResponseIsAsExpected(Response response) {
    assertResponseIsAsExpected(response, Status.OK);
  }

  public static void assertResponseIsAsExpected(Response response, Status status) {
    assertThat(response.getStatus(), is(status.getStatusCode()));
    List<Object> correlationIdHeaders =
        response.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY);
    assertThat(correlationIdHeaders, hasSize(1));
    assertThat(correlationIdHeaders.get(0), is(CORRELATION_ID));
    assertThat(response.getEntity(), is(USER_DTO_ONE));
  }

  public static void assertCollectionResponseIsAsExpected(Response response) {
    assertThat(response.getStatus(), is(Status.OK.getStatusCode()));
    List<Object> correlationIdHeaders =
        response.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY);
    assertThat(correlationIdHeaders, hasSize(1));
    assertThat(correlationIdHeaders.get(0), is(CORRELATION_ID));
    Object entity = response.getEntity();
    assertThat(entity, instanceOf(List.class));
    @SuppressWarnings("unchecked")
    List<UserDto> actual = (List<UserDto>) entity;
    assertThat(actual, hasSize(USER_RESPONSES.size()));
    assertThat(actual, is(USER_DTOS));
  }

  public static void assertCreateUserResponseIsAsExpected(Response response) {
    assertResponseIsAsExpected(response, Status.CREATED);
    List<Object> locationHeaders = response.getHeaders().get("LOCATION");
    assertThat(locationHeaders, hasSize(1));
    assertThat(
        locationHeaders.get(0),
        is(URI.create(String.format(UserEndpoint.GET_ONE_PATH_TEMPLATE, USER_ONE_NAME))));
  }

  private UserResponseValidator() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
