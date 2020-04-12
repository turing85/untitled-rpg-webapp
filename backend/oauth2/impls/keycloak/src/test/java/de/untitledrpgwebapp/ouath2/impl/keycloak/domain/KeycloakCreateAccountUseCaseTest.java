package de.untitledrpgwebapp.ouath2.impl.keycloak.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oauth2.boundary.response.AccountResponse;
import de.untitledrpgwebapp.ouath2.impl.keycloak.boundary.AccountMapper;
import de.untitledrpgwebapp.ouath2.impl.keycloak.domain.exception.KeycloakException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;

@DisplayName("Tests for KeycloakCreateAccountUseCase unit")
class KeycloakCreateAccountUseCaseTest {

  private final UUID correlationId = UUID.randomUUID();
  private final String name = "name";
  private final String email = "email";
  private final String password = "password";
  private CreateAccountRequest request;
  private AccountResponse response;
  private Response keycloakResponse;
  private UsersResource usersResource;
  private AccountMapper mapper;
  private KeycloakCreateAccountUseCase uut;

  @BeforeEach
  void setup() {
    request = CreateAccountRequest.builder()
        .name(name)
        .email(email)
        .password(password)
        .correlationId(correlationId)
        .build();
    response = AccountResponse.builder().build();

    keycloakResponse = mock(Response.class);
    when(keycloakResponse.getStatus()).thenReturn(Status.CREATED.getStatusCode());
    usersResource = mock(UsersResource.class);
    when(usersResource.create(any())).thenReturn(keycloakResponse);
    RealmResource realmResource = mock(RealmResource.class);
    when(realmResource.users()).thenReturn(usersResource);
    Keycloak keycloak = mock(Keycloak.class);
    when(keycloak.realm(anyString())).thenReturn(realmResource);
    mapper = mock(AccountMapper.class);
    when(mapper.requestToResponse(any())).thenReturn(response);

    uut = new KeycloakCreateAccountUseCase(keycloak, "realmName", mapper);
  }

  @Test
  @DisplayName("Should call UsersResource with the expected parameters and return the expected "
      + "response when everything is ok.")
  void shouldCallUsersResourceWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN: defaults

    // WHEN
    AccountResponse actual = uut.execute(request);

    // THEN
    assertThat(actual, is(notNullValue()));
    assertThat(actual, sameInstance(response));

    verifyUserResourceWasCalledWithExpectedParameters();
    verify(mapper).requestToResponse(request);
  }

  private void verifyUserResourceWasCalledWithExpectedParameters() {
    verify(usersResource).create(argThat(user -> {
      assertThat(user.getUsername(), is(name));
      assertThat(user.getEmail(), is(email));
      assertTrue(user.isEnabled());
      List<CredentialRepresentation> credentials = user.getCredentials();
      assertThat(credentials, hasSize(1));
      CredentialRepresentation credential = credentials.get(0);
      assertThat(credential.getType(), is(CredentialRepresentation.PASSWORD));
      assertThat(credential.getValue(), is(password));
      return true;
    }));
  }

  @Test
  @DisplayName("Should throw a KeycloakException with corresponding message when keycloak returns "
      + "unexpected status code.")
  void shouldThrowKeycloakExceptionWhenKeycloakReturnsUnexpectedStatusCode() {
    // GIVEN
    final String entity = "Oops";
    InputStream entityAsStream = new ByteArrayInputStream(entity.getBytes());
    int unexpectedStatusCode = Status.BAD_REQUEST.getStatusCode();
    when(keycloakResponse.getStatus()).thenReturn(unexpectedStatusCode);
    when(keycloakResponse.getEntity()).thenReturn(entityAsStream);

    String expectedMessage = String.format(
        KeycloakCreateAccountUseCase.KEYCLOAK_UNEXPECTED_RESPONSE_CODE_ERROR_MESSAGE_FORMAT,
        name,
        email,
        Status.CREATED.getStatusCode(),
        unexpectedStatusCode,
        entity);

    // WHEN
    KeycloakException exception = assertThrows(KeycloakException.class, () -> uut.execute(request));

    // THEN
    assertExceptionIsAsExpected(exception, expectedMessage, null);
  }

  @Test
  @DisplayName("Should throw a KeycloakException with corresponding message when keycloak returns "
      + "unexpected status code and the message body is unreadable.")
  void shouldThrowKeycloakExceptionWhenKeycloakReturnsUnexpectedStatusCodeAndMessageBodyIsUnreadable() {
    // GIVEN
    int unexpectedStatusCode = Status.BAD_REQUEST.getStatusCode();
    when(keycloakResponse.getStatus()).thenReturn(unexpectedStatusCode);
    when(keycloakResponse.getEntity()).thenReturn(new Object());

    String expectedMessage = String.format(
        KeycloakCreateAccountUseCase.KEYCLOAK_UNEXPECTED_RESPONSE_CODE_ERROR_MESSAGE_FORMAT,
        name,
        email,
        Status.CREATED.getStatusCode(),
        unexpectedStatusCode,
        KeycloakCreateAccountUseCase.ENTITY_UNREADABLE_MESSAGE);

    // WHEN
    KeycloakException exception = assertThrows(KeycloakException.class, () -> uut.execute(request));

    // THEN
    assertExceptionIsAsExpected(exception, expectedMessage, null);
  }

  private void assertExceptionIsAsExpected(
      KeycloakException exception,
      String expectedMessage,
      Throwable cause) {
    assertThat(exception.getMessage(), is(expectedMessage));
    assertThat(exception.getCorrelationId(), is(correlationId));
    assertThat(exception.getCause(), sameInstance(cause));
  }

  @Test
  @DisplayName("Should throw a KeycloakException with matching cause when Keycloak throws a "
      + "WebApplicationException.")
  void shouldThrowKeycloakExceptionWhenKeycloakThrowsWebApplicationException() {
    // GIVEN
    WebApplicationException cause = new WebApplicationException();
    when(usersResource.create(any())).thenThrow(cause);

    String expectedMessage = String.format(
        KeycloakCreateAccountUseCase.GENERAL_KEYCLOAK_ERROR_MESSAGE_FORMAT,
        name,
        email);

    // WHEN
    KeycloakException exception = assertThrows(KeycloakException.class, () -> uut.execute(request));

    // THEN
    assertExceptionIsAsExpected(exception, expectedMessage, exception.getCause());
  }

  @Test
  @DisplayName("Should throw a KeycloakException with matching cause when Keycloak throws an "
      + "unknown exception.")
  void shouldThrowKeycloakExceptionWhenKeycloakThrowsUnknownException() {
    // GIVEN
    final ProcessingException cause = new ProcessingException("Oops");
    when(usersResource.create(any())).thenThrow(cause);

    String expectedMessage = String.format(
        KeycloakCreateAccountUseCase.UNKNOWN_KEYCLOAK_ERROR_MESSAGE_FORMAT,
        name,
        email);

    // WHEN
    KeycloakException exception = assertThrows(KeycloakException.class, () -> uut.execute(request));

    // THEN
    assertExceptionIsAsExpected(exception, expectedMessage, cause);
  }
}