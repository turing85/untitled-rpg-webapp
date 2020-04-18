package de.untitledrpgwebapp.user.impl.quarkus.boundary.endpoint;

import static de.untitledrpgwebapp.impl.quarkus.testfixture.PageConfigDtoFixture.PAGE_CONFIG_DTO;
import static de.untitledrpgwebapp.user.impl.quarkus.boundary.testfixture.UserDtoFixture.USER_DTOS;
import static de.untitledrpgwebapp.user.impl.quarkus.boundary.testfixture.UserDtoFixture.USER_DTO_ONE;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_RESPONSES;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.domain.exception.EntityNotFoundException;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.CreateUserDto;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.mapper.UserMapper;
import java.security.Principal;
import java.util.Optional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for UserEndpoint unit.")
class UserEndpointTest {

  private FindAllUsersUseCase findAllUsers = mock(FindAllUsersUseCase.class);
  private FindUserByNameUseCase findUser = mock(FindUserByNameUseCase.class);
  private CreateUserUseCase createUser = mock(CreateUserUseCase.class);
  private UserMapper mapper = mock(UserMapper.class);

  private UserEndpoint uut;

  @BeforeEach
  void setup() {
    when(findAllUsers.execute(any())).thenReturn(USER_RESPONSES);

    when(findUser.execute(any()))
        .thenReturn(Optional.of(UserResponse.builder().name(USER_ONE_NAME).build()));
    when(createUser.execute(any()))
        .thenReturn(UserResponse.builder().name(USER_ONE_NAME).build());

    when(mapper.responsesToDtos(anyList())).thenReturn(USER_DTOS);
    when(mapper.responseToDto(any())).thenReturn(USER_DTO_ONE);

    uut = new UserEndpoint(findAllUsers, findUser, createUser, mapper);
  }

  @Test
  @DisplayName("Should call findAllUsers with the expected parameters and return the expected "
      + "response object when findAll is called.")
  void shouldCallFindAllUsersWithExpectedParameterAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN

    // WHEN
    Response response = uut.findAll(CORRELATION_ID, PAGE_CONFIG_DTO);

    // THEN
    UserResponseValidator.assertCollectionResponseIsAsExpected(response);

    verify(findAllUsers).execute(argThat(r -> {
      assertThat(r.getConfig(), sameInstance(PAGE_CONFIG_DTO));
      assertThat(r.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
    verify(mapper).responsesToDtos(USER_RESPONSES);
  }

  @Test
  @DisplayName("Should call findUser with the expected parameters and return the expected response "
      + "object when findByName is called.")
  void shouldCallFindUserWithExpectedParameterAndReturnExpectedResultWhenFindByNameIsCalled() {
    // GIVEN: defaults

    // WHEN
    Response response = uut.findByName(USER_ONE_NAME, CORRELATION_ID);

    // THEN
    UserResponseValidator.assertResponseIsAsExpected(response);

    verify(findUser).execute(argThat(r -> {
      assertThat(r.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
  }

  @Test
  @DisplayName("Should throw an EntityNotFoundException when findByName is called and no user with "
      + "the given name is found.")
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
      + "response object when createUser is called.")
  void shouldCallCreateUserWithExpectedParameterAndReturnExpectedResultWhenCreateUserIsCalled() {
    // GIVEN:
    CreateUserRequest request = CreateUserRequest.builder().name(USER_ONE_NAME).build();
    when(mapper.dtoToRequest(any(), any())).thenReturn(request);

    // WHEN
    Response response =
        uut.createUser(CreateUserDto.builder().name(USER_ONE_NAME).build(), CORRELATION_ID);

    // THEN
    UserResponseValidator.assertCreateUserResponseIsAsExpected(response);

    verify(createUser).execute(request);
  }

  @Test
  @DisplayName("Should call findUser with the expected parameters and return the expected response "
      + "when getMe is called.")
  void shouldCallFindUserWithExpectedParametersAndReturnExpectedResultWhenGetMeIsCalled() {
    // GIVEN
    when(findUser.execute(any()))
        .thenReturn(Optional.of(UserResponse.builder().name(USER_ONE_NAME).build()));
    when(mapper.responseToDto(any())).thenReturn(USER_DTO_ONE);
    Principal userPrincipal = mock(Principal.class);
    when(userPrincipal.getName()).thenReturn(USER_ONE_NAME);
    SecurityContext context = mock(SecurityContext.class);
    when(context.getUserPrincipal()).thenReturn(userPrincipal);

    // WHEN
    Response actual = uut.findCurrentUser(context, CORRELATION_ID);

    // THEN
    UserResponseValidator.assertResponseIsAsExpected(actual);

    verify(context).getUserPrincipal();
    verify(userPrincipal).getName();
    verify(findUser).execute(argThat(request -> {
      assertThat(request.getName(), is(USER_ONE_NAME));
      assertThat(request.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
  }
}