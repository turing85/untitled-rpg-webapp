package de.untitledrpgwebapp.oidc.impl.keycloak.domain;

import com.google.common.base.Objects;
import de.untitledrpgwebapp.oidc.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oidc.boundary.response.AccountResponse;
import de.untitledrpgwebapp.oidc.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.oidc.impl.keycloak.boundary.mapper.AccountMapper;
import de.untitledrpgwebapp.oidc.impl.keycloak.domain.exception.KeycloakException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

@Getter
@AllArgsConstructor
public class KeycloakCreateAccountUseCase implements CreateAccountUseCase {

  public static final String KEYCLOAK_UNEXPECTED_RESPONSE_CODE_ERROR_MESSAGE_FORMAT =
      "Error during creating the user %s with email %s in keycloak. Expected status code %s, but "
          + "got %s instead. Full response: %s";
  public static final String GENERAL_KEYCLOAK_ERROR_MESSAGE_FORMAT =
      "Error during creation of user %s with email %s in keycloak.";
  public static final String UNKNOWN_KEYCLOAK_ERROR_MESSAGE_FORMAT =
      "Unknown error during creation of user %s with email %s in keycloak.";
  public static final String ENTITY_UNREADABLE_MESSAGE = "(Entity unreadable)";

  private final Keycloak keycloak;
  private final String realmName;
  private final AccountMapper mapper;

  @Override
  public AccountResponse execute(CreateAccountRequest request) {
    UserRepresentation userRepresentation = constructUserRepresentation(request);
    UsersResource usersResource = keycloak.realm(realmName).users();
    String name = request.getName();
    String email = request.getEmail();
    try (Response response = usersResource.create(userRepresentation)) {
      validateKeycloakResponse(request, response);
    } catch (WebApplicationException webApplicationException) {
      throw new KeycloakException(
          String.format(GENERAL_KEYCLOAK_ERROR_MESSAGE_FORMAT, name, email),
          webApplicationException,
          request.getCorrelationId());
    } catch (ProcessingException processingException) {
      throw new KeycloakException(
          String.format(UNKNOWN_KEYCLOAK_ERROR_MESSAGE_FORMAT, name, email),
          processingException,
          request.getCorrelationId());
    }
    return mapper.requestToResponse(request);
  }

  private void validateKeycloakResponse(CreateAccountRequest request, Response response) {
    if (!Objects.equal(Status.CREATED.getStatusCode(), response.getStatus())) {
      String body;
      body = readMessageEntity(response);
      throw new KeycloakException(
          String.format(
              KEYCLOAK_UNEXPECTED_RESPONSE_CODE_ERROR_MESSAGE_FORMAT,
              request.getName(),
              request.getEmail(),
              Status.CREATED.getStatusCode(),
              response.getStatus(),
              body),
          request.getCorrelationId());
    }
  }

  private String readMessageEntity(Response response) {
    String body;
    try {
      body = new String(((InputStream) response.getEntity()).readAllBytes());
    } catch (ClassCastException | IOException e) {
      body = ENTITY_UNREADABLE_MESSAGE;
    }
    return body;
  }

  private UserRepresentation constructUserRepresentation(final CreateAccountRequest request) {
    final UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setEnabled(true);
    userRepresentation.setUsername(request.getName());
    userRepresentation.setEmail(request.getEmail());
    userRepresentation.setCredentials(
        List.of(constructCredentialsRepresentation(request.getPassword()))
    );
    return userRepresentation;
  }

  private CredentialRepresentation constructCredentialsRepresentation(String password) {
    CredentialRepresentation credentials = new CredentialRepresentation();
    credentials.setType(CredentialRepresentation.PASSWORD);
    credentials.setTemporary(false);
    credentials.setValue(password);
    return credentials;
  }
}
