package de.untitledrpgwebapp.user.impl.quarkus.beans;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.FindUserByNameInDatabaseUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindUserByNameUseCaseBean {

  @Produces
  public FindUserByNameUseCase findUser(UserRepository repository) {
    return new FindUserByNameInDatabaseUseCase(repository);
  }
}
