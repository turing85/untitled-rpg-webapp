package de.untitledrpgwebapp.language.impl.localstore.domain;

import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateLanguageInDatabaseUseCase implements CreateLanguageUseCase {

  private final LanguageRepository languageRepository;

  @Override
  public LanguageResponse execute(CreateLanguageRequest request) {
    return languageRepository.save(request).toBuilder()
        .correlationId(request.getCorrelationId())
        .build();
  }
}
