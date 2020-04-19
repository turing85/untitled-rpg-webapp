package de.untitledrpgwebapp.common.configuration.exceptionmapping;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ErrorResponseEntity {

  private final String message;
}
