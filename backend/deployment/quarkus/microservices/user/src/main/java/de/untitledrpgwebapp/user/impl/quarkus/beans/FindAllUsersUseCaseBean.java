package de.untitledrpgwebapp.user.impl.quarkus.beans;

import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.FindAllUsersInDatabaseUseCase;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.UserRepositoryProxy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindAllUsersUseCaseBean {

  @Produces
  public FindAllUsersUseCase findAllUsers(UserRepositoryProxy proxy) {
    return new FindAllUsersInDatabaseUseCase(proxy);
  }
}
