package de.untitledrpgwebapp.boundary.language.request.impl;

import de.untitledrpgwebapp.boundary.language.request.FindLanguageByCodeRequest;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class FindLanguageByCodeRequestImpl implements FindLanguageByCodeRequest {

  private final String code;
  private UUID correlationId;
}
