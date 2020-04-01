package de.untitledrpgwebapp.domain.language.impl;

import de.untitledrpgwebapp.boundary.language.LanguageRepository;
import de.untitledrpgwebapp.boundary.language.request.FetchLanguageByCodeRequest;
import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import de.untitledrpgwebapp.domain.language.FetchLanguageByCodeUseCase;
import java.util.Optional;

public class FetchLanguageByCodeUseCaseImpl implements FetchLanguageByCodeUseCase {

  private final LanguageRepository languageRepository;

  public FetchLanguageByCodeUseCaseImpl(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }


  @Override
  public Optional<LanguageResponseBuilder> execute(FetchLanguageByCodeRequest request) {
    return languageRepository.findByCode(request.getCode())
        .map(language -> language.setCorrelationId(request.getCorrelationId()));
  }
}
