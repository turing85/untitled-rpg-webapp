package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ErrorResponseEntity {

  private final String message;
}
