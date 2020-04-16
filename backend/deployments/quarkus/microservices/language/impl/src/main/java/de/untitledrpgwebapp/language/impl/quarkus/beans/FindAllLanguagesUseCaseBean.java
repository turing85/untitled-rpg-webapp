package de.untitledrpgwebapp.language.impl.quarkus.beans;

import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import de.untitledrpgwebapp.language.impl.localstore.domain.FindAllLanguagesInDatabaseUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindAllLanguagesUseCaseBean {

  @Produces
  public FindAllLanguagesUseCase findAllLanguages(LanguageDao dao) {
    return new FindAllLanguagesInDatabaseUseCase(dao);
  }
}
