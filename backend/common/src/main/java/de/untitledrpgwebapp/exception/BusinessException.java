package de.untitledrpgwebapp.exception;

import java.util.UUID;

public abstract class BusinessException extends RuntimeException {

  private final UUID correlationId;

  public BusinessException(String message, UUID correlationId) {
    super(message);
    this.correlationId = correlationId;
  }

  public BusinessException(String message, Throwable cause, UUID correlationId) {
    super(message, cause);
    this.correlationId = correlationId;
  }

  public UUID getCorrelationId() {
    return correlationId;
  }
}
