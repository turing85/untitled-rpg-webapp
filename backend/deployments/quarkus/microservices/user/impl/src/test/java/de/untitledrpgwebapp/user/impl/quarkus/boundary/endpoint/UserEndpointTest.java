package de.untitledrpgwebapp.user.impl.quarkus.boundary.endpoint;

import static de.untitledrpgwebapp.impl.quarkus.testfixture.PageConfigDtoFixture.PAGE_CONFIG_DTO;
import static de.untitledrpgwebapp.user.impl.quarkus.boundary.testfixture.UserDtoFixture.DTOS;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_NAMES;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_RESPONSES;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.domain.exception.EntityNotFoundException;
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
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for UserEndpoint unit.")
class UserEndpointTest {

  private FindAllUsersUseCase findAllUsers;
  private FindUserByNameUseCase findUser;
  private CreateUserUseCase createUser;
  private UserMapper mapper;

  private UserEndpoint uut;

  @BeforeEach
  void setup() {
    findAllUsers = mock(FindAllUsersUseCase.class);
    findUser = mock(FindUserByNameUseCase.class);

    createUser = mock(CreateUserUseCase.class);
    when(findAllUsers.execute(any())).thenReturn(USER_RESPONSES);

    findUser = mock(FindUserByNameUseCase.class);
    when(findUser.execute(any()))
        .thenReturn(Optional.of(UserResponse.builder().name(USER_ONE_NAME).build()));
    when(createUser.execute(any()))
        .thenReturn(UserResponse.builder().name(USER_ONE_NAME).build());

    mapper = mock(UserMapper.class);
    when(mapper.responsesToDtos(anyList())).thenReturn(DTOS);
    when(mapper.responseToDto(any())).thenReturn(UserDto.builder().name(USER_ONE_NAME).build());

    uut = new UserEndpoint(findAllUsers, findUser, createUser, mapper);
  }

  @Test
  @DisplayName("Should call findAllUsers with the expected parameters and return the expected "
      + "response object.")
  void shouldCallFindAllUsersWithExpectedParameterAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN

    // WHEN
    Response response = uut.findAll(CORRELATION_ID, PAGE_CONFIG_DTO);

    // THEN
    assertCollectionResponseIsAsExpected(response);

    verify(findAllUsers).execute(argThat(r -> {
      assertThat(r.getConfig(), sameInstance(PAGE_CONFIG_DTO));
      assertThat(r.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
    verify(mapper).responsesToDtos(USER_RESPONSES);
  }

  @Test
  @DisplayName("Should call findUser with the expected parameters and return the expected response "
      + "object.")
  void shouldCallFindUserWithExpectedParameterAndReturnExpectedResultWhenFindByNameIsCalled() {
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
  @DisplayName("Should throw an EntityNotFoundException if no user with the given name is "
      + "found.")
  void shouldThrowDependencyNotFoundExceptionWhenLanguageIsNotFound() {
    // GIVEN
    when(findUser.execute(any())).thenReturn(Optional.empty());

    String expectedMessage =
        String.format(EntityNotFoundException.MESSAGE_FORMAT, "user", "name", USER_ONE_NAME);

    // WHEN
    EntityNotFoundException exception = assertThrows(
        EntityNotFoundException.class,
        () -> uut.findByName(USER_ONE_NAME, CORRELATION_ID));

    // THEN
    assertThat(exception.getMessage(), is(expectedMessage));
  }

  @Test
  @DisplayName("Should call createUser with the expected parameters and return the expected "
      + "response object.")
  void shouldCallCreateUserWithExpectedParameterAndReturnExpectedResultWhenCreateUserIsCalled() {
    // GIVEN:
    CreateUserRequest request = CreateUserRequest.builder().name(USER_ONE_NAME).build();
    when(mapper.dtoToRequest(any(), any())).thenReturn(request);

    // WHEN
    Response response =
        uut.createUser(new CreateUserDto().setName(USER_ONE_NAME), CORRELATION_ID);

    // THEN
    assertCreateUserResponseIsAsExpected(response);

    verify(createUser).execute(request);
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
    assertThat(actual, hasSize(USER_RESPONSES.size()));
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