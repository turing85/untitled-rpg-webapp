package de.untitledrpgwebapp.language.boundary.response;

import de.untitledrpgwebapp.common.boundary.Correlated;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public class LanguageResponse implements Correlated {

  final String tag;
  final String name;
  final UUID correlationId;
}
