package de.untitledrpgwebapp.language.impl.quarkus.beans;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import de.untitledrpgwebapp.language.impl.localstore.domain.FindLanguageByTagInDatabaseUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindLanguageByTagUseCaseBean {

  @Produces
  public FindLanguageByTagUseCase findLanguage(LanguageDao dao) {
    return new FindLanguageByTagInDatabaseUseCase(dao);
  }
}
