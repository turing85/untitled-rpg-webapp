package de.untitledrpgwebapp.common.domain.exception;

import java.util.UUID;

public class EntityAlreadyExistsException extends BusinessException {
  public static final String MESSAGE_FORMAT = "A %s with %s \"%s\" already exists.";

  public EntityAlreadyExistsException(
      String typeName,
      String attribute,
      String value,
      UUID correlationId) {
    super(String.format(MESSAGE_FORMAT, typeName, attribute, value), correlationId);
  }

  public static EntityAlreadyExistsException userWithName(String name, UUID correlationId) {
    return new EntityAlreadyExistsException("user", "name", name, correlationId);
  }

  public static EntityAlreadyExistsException userWithEmail(String email, UUID correlationId) {
    return new EntityAlreadyExistsException("user", "email", email, correlationId);
  }

  public static EntityAlreadyExistsException languageWithTag(String tag, UUID correlationId) {
    return new EntityAlreadyExistsException("language", "tag", tag, correlationId);
  }
}
