package de.untitledrpgwebapp.language.exception;

import java.util.UUID;

public class LanguageNotFoundException extends BusinessException {

  public static final String MESSAGE_FORMAT = "No language with code %s found.";

  public LanguageNotFoundException(String languageCode, final UUID correlationId) {
    super(String.format(MESSAGE_FORMAT, languageCode), correlationId);
  }
}
