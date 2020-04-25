package de.untitledrpgwebapp.user.bean.proxy;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.oidc.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.domain.CreateUserInDatabaseUseCase;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.eclipse.microprofile.opentracing.Traced;
import org.mapstruct.factory.Mappers;

@Traced
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserUseCaseProxy implements CreateUserUseCase {

  @Delegate
  private final CreateUserInDatabaseUseCase createUser;

  /**
   * Bean constructor.
   * @param dao the dao use by this bean.
   * @param findLanguage the find language by tag use case used by this bean.
   * @param createAccount the create account use case used by this bean.
   */
  @Inject
  public CreateUserUseCaseProxy(
      UserDao dao,
      FindLanguageByTagUseCase findLanguage,
      CreateAccountUseCase createAccount) {
    this(new CreateUserInDatabaseUseCase(
        Mappers.getMapper(UserMapper.class),
        dao,
        findLanguage,
        createAccount));
  }




}
