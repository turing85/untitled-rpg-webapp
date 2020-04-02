package de.untitledrpgwebapp.boundary.domain.ouath2.impl;

import com.google.common.base.Objects;
import de.untitledrpgwebapp.boundary.domain.ouath2.impl.exception.KeycloakException;
import de.untitledrpgwebapp.boundary.oauth2.request.CreateAccountRequest;
import de.untitledrpgwebapp.boundary.oauth2.response.AccountResponseBuilder;
import de.untitledrpgwebapp.domain.oauth2.CreateAccountUseCase;
import java.util.List;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

public class KeycloakCreateAccountUseCase implements CreateAccountUseCase {

  public static final String KEYCLOAK_UNEXPECTED_RESPONSE_CODE_FORMAT =
      "Error during creating the user %s with email %s in keycloak. Expected status code %s, but "
          + "got %s. Full entity:\n%s";
  public static final String GENERAL_KEYCLOAK_ERROR_FORMAT =
      "Error during creating the user %s with email %s in keycloak.";
  public static final String UNKNOWN_KEYCLOAK_ERROR_FORMAT =
      "Unknown error during creating the user %s with email %s in keycloak.";

  private final Keycloak keycloak;
  private final String realmName;

  public KeycloakCreateAccountUseCase(Keycloak keycloak, String realmName) {
    this.keycloak = keycloak;
    this.realmName = realmName;
  }

  @Override
  public AccountResponseBuilder execute(CreateAccountRequest request) {
    final UserRepresentation userRepresentation = constructUserRepresentation(request);
    final UsersResource usersResource = keycloak.realm(realmName).users();
    try (Response response = usersResource.create(userRepresentation)) {
      validateKeycloakResponse(request, response);
    } catch (WebApplicationException webApplicationException) {
      throw new KeycloakException(
          String.format(GENERAL_KEYCLOAK_ERROR_FORMAT, request.getName(), request.getEmail()),
          webApplicationException,
          request.getCorrelationId());
    } catch (ProcessingException processingException) {
      throw new KeycloakException(
          String.format(UNKNOWN_KEYCLOAK_ERROR_FORMAT, request.getName(), request.getEmail()),
          processingException,
          request.getCorrelationId());
    }
    return request.getAccountResponseBuilder().setCorrelationId(request.getCorrelationId());
  }

  private void validateKeycloakResponse(CreateAccountRequest request, Response response) {
    if (!Objects.equal(Status.CREATED.getStatusCode(), response.getStatus())) {
      throw new KeycloakException(
          String.format(
              KEYCLOAK_UNEXPECTED_RESPONSE_CODE_FORMAT,
              request.getName(),
              request.getEmail(),
              Status.CREATED.getStatusCode(),
              response.getStatus(),
              response.getEntity()),
          request.getCorrelationId());
    }
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
