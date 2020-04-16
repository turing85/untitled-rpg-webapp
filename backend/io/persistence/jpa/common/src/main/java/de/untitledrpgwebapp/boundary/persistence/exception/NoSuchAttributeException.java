package de.untitledrpgwebapp.boundary.persistence.exception;

public class NoSuchAttributeException extends IllegalArgumentException {
  public static final String MESSAGE_TEMPLATE = "No such attribute: \"%s\"";

  public NoSuchAttributeException(String attributeName) {
    super(String.format(MESSAGE_TEMPLATE, attributeName));
  }
}
