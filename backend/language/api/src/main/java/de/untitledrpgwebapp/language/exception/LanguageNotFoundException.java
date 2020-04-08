package de.untitledrpgwebapp.language.exception;

import java.util.UUID;

public class LanguageNotFoundException extends BusinessException {

  public static final String MESSAGE_FORMAT = "No language with tag %s found.";

  public LanguageNotFoundException(String tag, final UUID correlationId) {
    super(String.format(MESSAGE_FORMAT, tag), correlationId);
  }
}
