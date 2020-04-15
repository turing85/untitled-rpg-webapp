package de.untitledrpgwebapp.domain.exception;

import java.util.UUID;

public abstract class NotFoundException extends BusinessException {
  NotFoundException(String message, UUID correlationId) {
    super(message, correlationId);
  }
}
