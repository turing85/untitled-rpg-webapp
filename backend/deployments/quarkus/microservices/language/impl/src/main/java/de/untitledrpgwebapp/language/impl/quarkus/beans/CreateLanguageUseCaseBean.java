package de.untitledrpgwebapp.language.impl.quarkus.beans;

import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import de.untitledrpgwebapp.language.impl.localstore.domain.CreateLanguageInDatabaseUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class CreateLanguageUseCaseBean {

  @Produces
  CreateLanguageUseCase createLanguage(LanguageDao dao) {
    return new CreateLanguageInDatabaseUseCase(dao);
  }
}
