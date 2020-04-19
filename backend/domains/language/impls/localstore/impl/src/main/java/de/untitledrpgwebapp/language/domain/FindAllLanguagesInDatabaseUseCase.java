package de.untitledrpgwebapp.language.domain;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.boundary.request.FindAllLanguagesRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindAllLanguagesInDatabaseUseCase implements FindAllLanguagesUseCase {

  private final LanguageDao dao;

  @Transactional
  @Override
  public Collection<LanguageResponse> execute(FindAllLanguagesRequest request) {
    return dao.findAll(request.getConfig())
        .stream()
        .map(language -> language.toBuilder().correlationId(request.getCorrelationId()).build())
        .collect(Collectors.toList());
  }
}
