package de.untitledrpgwebapp.user.impl.quarkus.beans;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.oauth2.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.impl.localstore.domain.CreateUserInDatabaseUseCase;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.UserRepositoryProxy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.mapstruct.factory.Mappers;

@ApplicationScoped
public class CreateUserUseCaseBean {

  @Produces
  public CreateUserUseCase createUser(
      UserRepositoryProxy proxy,
      CreateAccountUseCase createAccount,
      FindLanguageByTagUseCase findLanguage) {
    return new CreateUserInDatabaseUseCase(
        Mappers.getMapper(UserMapper.class),
        proxy,
        findLanguage,
        createAccount);
  }
}
