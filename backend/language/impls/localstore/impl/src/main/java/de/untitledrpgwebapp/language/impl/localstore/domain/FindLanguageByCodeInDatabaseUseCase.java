package de.untitledrpgwebapp.language.impl.localstore.domain;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByCodeUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindLanguageByCodeInDatabaseUseCase implements FindLanguageByCodeUseCase {

  private final LanguageRepository repository;

  @Override
  public Optional<LanguageResponse> execute(FindLanguageByCodeRequest request) {
    return repository.findByCode(request.getCode())
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build());
  }
}
