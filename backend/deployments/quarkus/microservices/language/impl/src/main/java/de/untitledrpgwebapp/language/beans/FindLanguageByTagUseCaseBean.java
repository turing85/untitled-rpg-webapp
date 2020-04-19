package de.untitledrpgwebapp.language.beans;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagInDatabaseUseCase;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindLanguageByTagUseCaseBean {

  @Produces
  public FindLanguageByTagUseCase findLanguage(LanguageDao dao) {
    return new FindLanguageByTagInDatabaseUseCase(dao);
  }
}
