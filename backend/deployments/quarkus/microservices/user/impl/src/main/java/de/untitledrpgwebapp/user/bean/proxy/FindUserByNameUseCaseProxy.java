package de.untitledrpgwebapp.user.bean.proxy;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.FindUserByNameInDatabaseUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.eclipse.microprofile.opentracing.Traced;

@Traced
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class FindUserByNameUseCaseProxy implements FindUserByNameUseCase {

  @Delegate
  private final FindUserByNameInDatabaseUseCase findByName;

  @Inject
  public FindUserByNameUseCaseProxy(UserDao dao) {
    this(new FindUserByNameInDatabaseUseCase(dao));
  }
}
