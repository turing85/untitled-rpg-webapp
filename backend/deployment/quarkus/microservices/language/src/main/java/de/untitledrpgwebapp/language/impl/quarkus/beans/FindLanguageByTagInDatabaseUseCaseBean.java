package de.untitledrpgwebapp.language.impl.quarkus.beans;

import de.untitledrpgwebapp.language.impl.localstore.domain.FindLanguageByTagInDatabaseUseCase;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.LanguageRepositoryProxy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindLanguageByTagInDatabaseUseCaseBean {

  @Produces
  public FindLanguageByTagInDatabaseUseCase findLanguage(LanguageRepositoryProxy proxy) {
    return new FindLanguageByTagInDatabaseUseCase(proxy);
  }
}
