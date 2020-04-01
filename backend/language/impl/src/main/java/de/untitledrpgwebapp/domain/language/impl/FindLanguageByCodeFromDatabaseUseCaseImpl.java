package de.untitledrpgwebapp.domain.language.impl;

import de.untitledrpgwebapp.boundary.language.LanguageRepository;
import de.untitledrpgwebapp.boundary.language.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import de.untitledrpgwebapp.domain.language.FindLanguageByCodeUseCase;
import java.util.Optional;

public class FindLanguageByCodeFromDatabaseUseCaseImpl implements FindLanguageByCodeUseCase {

  private final LanguageRepository languageRepository;

  public FindLanguageByCodeFromDatabaseUseCaseImpl(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }

  @Override
  public Optional<LanguageResponseBuilder> execute(FindLanguageByCodeRequest request) {
    return languageRepository.findByCode(request.getCode())
        .map(language -> language.setCorrelationId(request.getCorrelationId()));
  }
}
