package de.untitledrpgwebapp.domain.exception;

import java.util.UUID;

public abstract class BadRequestException extends BusinessException {

  public BadRequestException(String message, UUID correlationId) {
    super(message, correlationId);
  }
}
