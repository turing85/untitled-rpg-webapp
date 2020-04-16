package de.untitledrpgwebapp.language.impl.localstore.domain;

import de.untitledrpgwebapp.language.boundary.request.FindAllLanguagesRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindAllLanguagesInDatabaseUseCase implements FindAllLanguagesUseCase {

  private final LanguageDao dao;

  @Override
  public Collection<LanguageResponse> execute(FindAllLanguagesRequest request) {
    return dao.findAll(request.getConfig())
        .stream()
        .map(language -> language.toBuilder().correlationId(request.getCorrelationId()).build())
        .collect(Collectors.toList());
  }
}
