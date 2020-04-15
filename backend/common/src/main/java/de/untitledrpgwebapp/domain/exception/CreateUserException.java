package de.untitledrpgwebapp.domain.exception;

import java.util.UUID;

public class CreateUserException extends BadRequestException {
  public CreateUserException(String message, UUID correlationId) {
    super(message, correlationId);
  }
}
