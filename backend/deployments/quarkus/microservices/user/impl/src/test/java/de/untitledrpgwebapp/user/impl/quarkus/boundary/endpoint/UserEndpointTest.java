package de.untitledrpgwebapp.user.impl.quarkus.boundary.endpoint;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.CreateUserDto;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.UserDto;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.mapper.UserMapper;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for UserEndpoint unit")
class UserEndpointTest {

  private static final UUID CORRELATION_ID = UUID.randomUUID();
  private static final String USER_ONE_NAME = "userOneName";
  private static final String USER_TWO_NAME = "userTwoName";
  private static final List<String> USER_NAMES = List.of(USER_ONE_NAME, USER_TWO_NAME);

  private static final List<UserResponse> FOUND = List.of(
      UserResponse.builder().name(USER_ONE_NAME).build(),
      UserResponse.builder().name(USER_TWO_NAME).build());

  private static final List<UserDto> DTOS = List.of(
      UserDto.builder().name(USER_ONE_NAME).build(),
      UserDto.builder().name(USER_TWO_NAME).build());

  private FindAllUsersUseCase findAllUsers;
  private FindUserByNameUseCase findUser;
  private UserMapper mapper;

  private UserEndpoint uut;

  @BeforeEach
  void setup() {
    findAllUsers = mock(FindAllUsersUseCase.class);
    findUser = mock(FindUserByNameUseCase.class);

    CreateUserUseCase createUser = mock(CreateUserUseCase.class);
    when(findAllUsers.execute(any())).thenReturn(FOUND);

    findUser = mock(FindUserByNameUseCase.class);
    when(findUser.execute(any()))
        .thenReturn(Optional.of(UserResponse.builder().name(USER_ONE_NAME).build()));
    when(createUser.execute(any()))
        .thenReturn(UserResponse.builder().name(USER_ONE_NAME).build());

    mapper = mock(UserMapper.class);
    when(mapper.responsesToDtos(anyList())).thenReturn(DTOS);
    when(mapper.responseToDto(any())).thenReturn(UserDto.builder().name(USER_ONE_NAME).build());
    when(mapper.dtoToRequest(any()))
        .thenReturn(CreateUserRequest.builder().name(USER_ONE_NAME).build());

    uut = new UserEndpoint(findAllUsers, findUser, createUser, mapper);
  }

  @Test
  @DisplayName("Should call findAllUsers with the expected parameters and return the expected "
      + "response object")
  void shouldCallFindAllUsersWithExpectedParameterAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN: defaults

    // WHEN
    Response response = uut.findAll(CORRELATION_ID);

    // THEN
    assertCollectionResponseIsAsExpected(response);

    verify(findAllUsers).execute(argThat(r -> {
      assertThat(r.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
    verify(mapper).responsesToDtos(FOUND);
  }

  @Test
  void shouldCallFindUserWithExpectedParameterAndReturnExpectedREsultWhenFindByNameIsCalled() {
    // GIVEN: defaults

    // WHEN
    Response response = uut.findByName(USER_ONE_NAME, CORRELATION_ID);

    // THEN
    assertResponseIsAsExpected(response);

    verify(findUser).execute(argThat(r -> {
      assertThat(r.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
  }

  @Test
  void shouldCallCreateUserWithExpectedParameterAndReturnExpectedResultWhenCreateUserIsCalled() {
    // GIVEN: defaults

    // WHEN
    Response response =
        uut.createUser(CreateUserDto.builder().name(USER_ONE_NAME).build(), CORRELATION_ID);

    // THEN
    assertCreateUserResponseIsAsExpected(response);
  }

  private void assertResponseIsAsExpected(Response response) {
    assertResponseIsAsExpected(response, Status.OK);
  }

  private void assertResponseIsAsExpected(Response response, Status status) {
    assertThat(response.getStatus(), is(status.getStatusCode()));
    List<Object> correlationIdHeaders =
        response.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY);
    assertThat(correlationIdHeaders, hasSize(1));
    assertThat(correlationIdHeaders.get(0), is(CORRELATION_ID));
    Object entity = response.getEntity();
    assertThat(entity, instanceOf(UserDto.class));
    UserDto actual = (UserDto) entity;
    assertThat(actual.getName(), is(USER_ONE_NAME));
  }

  private void assertCollectionResponseIsAsExpected(Response response) {
    assertThat(response.getStatus(), is(Status.OK.getStatusCode()));
    List<Object> correlationIdHeaders =
        response.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY);
    assertThat(correlationIdHeaders, hasSize(1));
    assertThat(correlationIdHeaders.get(0), is(CORRELATION_ID));
    Object entity = response.getEntity();
    assertThat(entity, instanceOf(List.class));
    @SuppressWarnings("unchecked")
    List<UserDto> actual = (List<UserDto>) entity;
    assertThat(actual, hasSize(FOUND.size()));
    assertThat(
        actual.stream().map(UserDto::getName).collect(Collectors.toList()),
        containsInAnyOrder(USER_NAMES.toArray()));
  }

  private void assertCreateUserResponseIsAsExpected(Response response) {
    assertResponseIsAsExpected(response, Status.CREATED);
    List<Object> locationHeaders = response.getHeaders().get("LOCATION");
    assertThat(locationHeaders, hasSize(1));
    assertThat(
        locationHeaders.get(0),
        is(URI.create(String.format(UserEndpoint.GET_ONE_PATH_TEMPLATE, USER_ONE_NAME))));
  }
}