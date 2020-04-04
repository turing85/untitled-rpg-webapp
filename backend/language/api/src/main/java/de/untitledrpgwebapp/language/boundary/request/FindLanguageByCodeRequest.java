package de.untitledrpgwebapp.language.boundary.request;

import de.untitledrpgwebapp.boundary.Correlated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class FindLanguageByCodeRequest extends Correlated<FindLanguageByCodeRequest> {

  String code;
}
