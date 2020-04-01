package de.untitledrpgwebapp.boundary.user.request.impl;

import de.untitledrpgwebapp.boundary.language.request.FetchLanguageByCodeRequest;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class FetchLanguageByCodeRequestImpl implements FetchLanguageByCodeRequest {

  private final String code;
  private UUID correlationId;
}
