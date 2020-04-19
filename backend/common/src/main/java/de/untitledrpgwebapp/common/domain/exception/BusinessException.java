package de.untitledrpgwebapp.common.domain.exception;

import java.util.UUID;
import lombok.Getter;

@Getter
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
}
