package de.untitledrpgwebapp.language.domain;

import de.untitledrpgwebapp.common.domain.exception.EntityAlreadyExistsException;
import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
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

    verifyTagIsAvailable(request.getTag(), correlationId);

    return dao.save(request).toBuilder()
        .correlationId(request.getCorrelationId())
        .build();
  }

  private void verifyTagIsAvailable(String tag, UUID correlationId) {
    if (dao.findByTag(tag).isPresent()) {
      throw EntityAlreadyExistsException.languageWithTag(tag, correlationId);
    }
  }
}
