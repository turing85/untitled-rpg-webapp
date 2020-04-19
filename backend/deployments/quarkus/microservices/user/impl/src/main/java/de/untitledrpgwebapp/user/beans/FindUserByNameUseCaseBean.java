package de.untitledrpgwebapp.user.beans;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindUserByNameInDatabaseUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FindUserByNameUseCaseBean {

  @Produces
  public FindUserByNameUseCase findUser(UserDao dao) {
    return new FindUserByNameInDatabaseUseCase(dao);
  }
}
