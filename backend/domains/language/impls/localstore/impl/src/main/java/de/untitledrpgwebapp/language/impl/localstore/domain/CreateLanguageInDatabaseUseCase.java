package de.untitledrpgwebapp.language.impl.localstore.domain;

import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateLanguageInDatabaseUseCase implements CreateLanguageUseCase {

  private final LanguageDao dao;

  @Transactional
  @Override
  public LanguageResponse execute(CreateLanguageRequest request) {
    return dao.save(request).toBuilder()
        .correlationId(request.getCorrelationId())
        .build();
  }
}
