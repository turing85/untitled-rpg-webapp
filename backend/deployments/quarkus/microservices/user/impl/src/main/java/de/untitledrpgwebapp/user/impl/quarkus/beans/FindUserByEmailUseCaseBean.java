package de.untitledrpgwebapp.user.impl.quarkus.beans;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindUserByEmailUseCase;
import de.untitledrpgwebapp.user.impl.localstore.domain.FindUserByEmailInDatabaseUseCase;
import javax.enterprise.inject.Produces;

public class FindUserByEmailUseCaseBean {

  @Produces
  public FindUserByEmailUseCase findByEmail(UserDao userDao) {
    return new FindUserByEmailInDatabaseUseCase(userDao);
  }
}
