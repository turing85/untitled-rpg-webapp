package de.untitledrpgwebapp.language.boundary.request;

import de.untitledrpgwebapp.boundary.Correlated;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CreateLanguageRequest implements Correlated {
  private final String tag;
  private final String name;
  private final UUID correlationId;
}
