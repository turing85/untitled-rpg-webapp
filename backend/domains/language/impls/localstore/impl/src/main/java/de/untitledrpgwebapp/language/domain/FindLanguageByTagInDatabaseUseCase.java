package de.untitledrpgwebapp.language.domain;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindLanguageByTagInDatabaseUseCase implements FindLanguageByTagUseCase {

  private final LanguageDao dao;

  @Transactional
  @Override
  public Optional<LanguageResponse> execute(FindLanguageByTagRequest request) {
    return dao.findByTag(request.getTag())
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build());
  }
}
