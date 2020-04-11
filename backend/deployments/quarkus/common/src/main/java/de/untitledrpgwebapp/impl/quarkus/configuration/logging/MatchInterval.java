package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MatchInterval {
  private final int start;
  private final int end;
}
