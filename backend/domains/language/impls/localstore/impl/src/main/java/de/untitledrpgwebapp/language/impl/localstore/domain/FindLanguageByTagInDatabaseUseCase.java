package de.untitledrpgwebapp.language.impl.localstore.domain;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindLanguageByTagInDatabaseUseCase implements FindLanguageByTagUseCase {

  private final LanguageDao dao;

  @Override
  public Optional<LanguageResponse> execute(FindLanguageByTagRequest request) {
    return dao.findByTag(request.getTag())
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build());
  }
}
