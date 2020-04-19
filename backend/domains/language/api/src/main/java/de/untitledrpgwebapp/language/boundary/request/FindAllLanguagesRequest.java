package de.untitledrpgwebapp.language.boundary.request;

import de.untitledrpgwebapp.boundary.Correlated;
import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindAllLanguagesRequest implements Correlated {

  private final UUID correlationId;
  private final PageAndSortConfig config;
}
