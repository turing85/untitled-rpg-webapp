package de.untitledrpgwebapp.user.impl.quarkus.beans;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.FindAllUsersInDatabaseUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindAllUsersUseCaseBean {

  @Produces
  public FindAllUsersUseCase findAllUsers(UserDao dao) {
    return new FindAllUsersInDatabaseUseCase(dao);
  }
}
