package de.untitledrpgwebapp.language.beans;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.domain.FindAllLanguagesInDatabaseUseCase;
import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindAllLanguagesUseCaseBean {

  @Produces
  public FindAllLanguagesUseCase findAllLanguages(LanguageDao dao) {
    return new FindAllLanguagesInDatabaseUseCase(dao);
  }
}
