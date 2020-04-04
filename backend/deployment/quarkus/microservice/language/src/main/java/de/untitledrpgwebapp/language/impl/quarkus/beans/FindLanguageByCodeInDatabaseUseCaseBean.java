package de.untitledrpgwebapp.language.impl.quarkus.beans;

import de.untitledrpgwebapp.language.impl.localstore.domain.FindLanguageByCodeInDatabaseUseCase;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.LanguageRepositoryProxy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindLanguageByCodeInDatabaseUseCaseBean {

  @Produces
  public FindLanguageByCodeInDatabaseUseCase findLanguage(LanguageRepositoryProxy repositoryProxy) {
    return new FindLanguageByCodeInDatabaseUseCase(repositoryProxy);
  }
}
