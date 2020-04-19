package de.untitledrpgwebapp.domain.exception;

import java.util.UUID;

public class EntityNotFoundException extends BusinessException {
  public static final String MESSAGE_FORMAT = "No %s with %s \"%s\" found.";

  public EntityNotFoundException(
      String typeName,
      String attribute,
      String value,
      UUID correlationId) {
    super(String.format(MESSAGE_FORMAT, typeName, attribute, value), correlationId);
  }

  public static EntityNotFoundException userWithName(String name, UUID correlationId) {
    return new EntityNotFoundException("user", "name", name, correlationId);
  }

  public static EntityNotFoundException languageWithTag(String tag, UUID correlationId) {
    return new EntityNotFoundException("language", "tag", tag, correlationId);
  }
}
