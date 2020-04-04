package de.untitledrpgwebapp.language.boundary.response;

import de.untitledrpgwebapp.boundary.Correlated;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class LanguageResponse extends Correlated<LanguageResponse> {

  final String code;
  final String name;
}
