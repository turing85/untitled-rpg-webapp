package de.untitledrpgwebapp.user.bean.proxy;

import de.untitledrpgwebapp.oidc.boundary.mapper.AccountMapper;
import de.untitledrpgwebapp.oidc.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.oidc.domain.KeycloakCreateAccountUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;
import org.keycloak.admin.client.Keycloak;
import org.mapstruct.factory.Mappers;

@Traced
@Transactional
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateAccountUseCaseProxy implements CreateAccountUseCase {

  @Delegate
  private final KeycloakCreateAccountUseCase createAccount;

  /**
   * Bean constructor.
   *
   * @param keycloak
   *     the keycloak used by this bean.
   * @param realmName
   *     the realm name used by this bean.
   */
  @Inject
  public CreateAccountUseCaseProxy(
      Keycloak keycloak,
      @ConfigProperty(name = "keycloak.realm-name") String realmName) {
    this(new KeycloakCreateAccountUseCase(
        keycloak,
        realmName,
        Mappers.getMapper(AccountMapper.class)));
  }
}
