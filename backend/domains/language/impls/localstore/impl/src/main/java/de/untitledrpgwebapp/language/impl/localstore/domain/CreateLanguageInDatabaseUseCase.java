package de.untitledrpgwebapp.language.impl.localstore.domain;

import de.untitledrpgwebapp.domain.exception.EntityAlreadyExistsException;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import java.util.UUID;
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
    UUID correlationId = request.getCorrelationId();
    String tag = request.getTag();

    if (dao.findByTag(request.getTag()).isPresent()) {
      throw EntityAlreadyExistsException.languageWithTag(tag, correlationId);
    }

    return dao.save(request).toBuilder()
        .correlationId(request.getCorrelationId())
        .build();
  }
}
