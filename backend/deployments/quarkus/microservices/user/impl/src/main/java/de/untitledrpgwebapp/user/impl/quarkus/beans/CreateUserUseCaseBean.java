package de.untitledrpgwebapp.user.impl.quarkus.beans;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.oidc.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByEmailUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.impl.localstore.domain.CreateUserInDatabaseUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.mapstruct.factory.Mappers;

@ApplicationScoped
public class CreateUserUseCaseBean {

  /**
   * Produces the CreateUserUseCase bean.
   *
   * @param dao
   *     the UserDao used by this bean.
   * @param createAccount
   *     the link CreateAccountUseCase used by this bean.
   * @param findLanguage
   *     the FindLanguageByTagUseCase used by this bean.
   *
   * @return an injectable CreateUserUseCase bean.
   */
  @Produces
  public CreateUserUseCase createUser(
      UserDao dao,
      FindUserByNameUseCase findByName,
      FindUserByEmailUseCase findByEmail,
      FindLanguageByTagUseCase findLanguage,
      CreateAccountUseCase createAccount) {
    return new CreateUserInDatabaseUseCase(
        Mappers.getMapper(UserMapper.class),
        dao,
        findByName,
        findByEmail,
        findLanguage,
        createAccount);
  }
}
