package de.untitledrpgwebapp.user.bean.proxy;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindAllUsersInDatabaseUseCase;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.eclipse.microprofile.opentracing.Traced;

@Traced
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class FindAllUsersUseCaseProxy implements FindAllUsersUseCase {

  @Delegate
  private final FindAllUsersInDatabaseUseCase findAllUsers;

  @Inject
  public FindAllUsersUseCaseProxy(UserDao dao) {
    this(new FindAllUsersInDatabaseUseCase(dao));
  }
}
