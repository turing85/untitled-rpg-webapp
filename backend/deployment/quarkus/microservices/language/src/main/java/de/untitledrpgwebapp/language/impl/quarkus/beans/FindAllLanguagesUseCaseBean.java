package de.untitledrpgwebapp.language.impl.quarkus.beans;

import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import de.untitledrpgwebapp.language.impl.localstore.domain.FindAllLanguagesInDatabaseUseCase;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.LanguageRepositoryProxy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindAllLanguagesUseCaseBean {

  @Produces
  public FindAllLanguagesUseCase findLanguage(LanguageRepositoryProxy proxy) {
    return new FindAllLanguagesInDatabaseUseCase(proxy);
  }
}
