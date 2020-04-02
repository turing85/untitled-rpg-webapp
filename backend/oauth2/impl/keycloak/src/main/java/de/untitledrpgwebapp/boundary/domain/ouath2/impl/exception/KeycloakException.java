package de.untitledrpgwebapp.boundary.domain.ouath2.impl.exception;

import de.untitledrpgwebapp.exception.BusinessException;
import java.util.UUID;

public class KeycloakException extends BusinessException {

  public KeycloakException(String message, UUID correlationId) {
    super(message, correlationId);
  }

  public KeycloakException(String message, Throwable cause, UUID correlationId) {
    super(message, cause, correlationId);
  }
}
