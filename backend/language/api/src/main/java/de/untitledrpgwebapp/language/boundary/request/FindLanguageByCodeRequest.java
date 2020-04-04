package de.untitledrpgwebapp.language.boundary.request;

import de.untitledrpgwebapp.boundary.Correlated;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindLanguageByCodeRequest implements Correlated {

  final String code;
  final UUID correlationId;
}
