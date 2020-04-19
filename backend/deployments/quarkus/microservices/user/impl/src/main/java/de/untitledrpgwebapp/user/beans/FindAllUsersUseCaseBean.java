package de.untitledrpgwebapp.user.beans;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindAllUsersInDatabaseUseCase;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindAllUsersUseCaseBean {

  @Produces
  public FindAllUsersUseCase findAllUsers(UserDao dao) {
    return new FindAllUsersInDatabaseUseCase(dao);
  }
}
