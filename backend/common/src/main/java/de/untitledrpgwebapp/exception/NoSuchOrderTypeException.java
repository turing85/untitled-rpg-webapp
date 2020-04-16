package de.untitledrpgwebapp.exception;

public class NoSuchOrderTypeException extends IllegalArgumentException {
  public static final String MESSAGE_TEMPLATE = "No such order: \"%s\"";

  public NoSuchOrderTypeException(String orderTypeString) {
    super(String.format(MESSAGE_TEMPLATE, orderTypeString));
  }
}
