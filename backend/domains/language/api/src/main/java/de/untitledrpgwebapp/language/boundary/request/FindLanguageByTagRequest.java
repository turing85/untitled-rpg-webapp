package de.untitledrpgwebapp.language.boundary.request;

import de.untitledrpgwebapp.boundary.Correlated;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindLanguageByTagRequest implements Correlated {

  final String tag;
  final UUID correlationId;
}
