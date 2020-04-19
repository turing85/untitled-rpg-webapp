package de.untitledrpgwebapp.language.beans;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.domain.CreateLanguageInDatabaseUseCase;
import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class CreateLanguageUseCaseBean {

  @Produces
  CreateLanguageUseCase createLanguage(LanguageDao dao) {
    return new CreateLanguageInDatabaseUseCase(dao);
  }
}
