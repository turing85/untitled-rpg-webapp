package de.untitledrpgwebapp.domain.exception;

import java.util.UUID;

public class DependencyNotFoundException extends NotFoundException {
  public static final String MESSAGE_FORMAT = "No %s with %s \"%s\" found.";

  public DependencyNotFoundException(
      String type,
      String attribute,
      String value,
      UUID correlationId) {
    super(String.format(MESSAGE_FORMAT, type, attribute, value), correlationId);
  }

  public static DependencyNotFoundException languageWithTag(String tag, UUID correlationId) {
    return new DependencyNotFoundException("language", "tag", tag, correlationId);
  }
}
