package de.untitledrpgwebapp.user.impl.quarkus.beans;

import de.untitledrpgwebapp.oauth2.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.ouath2.impl.keycloak.boundary.AccountMapper;
import de.untitledrpgwebapp.ouath2.impl.keycloak.domain.KeycloakCreateAccountUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.mapstruct.factory.Mappers;

@ApplicationScoped
public class CreateAccountUseCaseBean {

  /**
   * Produces the CreateUserUseCase bean.
   *
   * @param keycloak
   *     the keycloak used by this bean.
   * @param realmName
   *     the realm name used by this bean.
   *
   * @return the CreateAccountUseCase bean.
   *
   * @see #keycloak(KeycloakBuilder, String, String, String, String)
   */
  @Produces
  CreateAccountUseCase createAccount(
      Keycloak keycloak,
      @ConfigProperty(name = "oauth2.realm.name") String realmName) {
    return new KeycloakCreateAccountUseCase(
        keycloak,
        realmName,
        Mappers.getMapper(AccountMapper.class));
  }

  /**
   * Produces the Keycloak bean.
   *
   * @param builder
   *     the keycloak builder to use.
   * @param serverUrl
   *     the url to the keycloak server.
   * @param realmName
   *     the realm name to use.
   * @param adminCliId
   *     the id of the client that has admin rights on the realm.
   * @param adminCliSecret
   *     the secret of that client.
   *
   * @return the Keycloak bean.
   */
  @Produces
  Keycloak keycloak(
      KeycloakBuilder builder,
      @ConfigProperty(name = "oauth2.server-url") String serverUrl,
      @ConfigProperty(name = "oauth2.realm.name") String realmName,
      @ConfigProperty(name = "oauth2.admin-cli.id") String adminCliId,
      @ConfigProperty(name = "oauth2.admin-cli.secret") String adminCliSecret) {
    return builder
        .serverUrl(serverUrl)
        .realm(realmName)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .clientId(adminCliId)
        .clientSecret(adminCliSecret)
        .build();
  }

  /**
   * Produces the standard keycloak builder.
   *
   * @return the KeycloakBuilder bean.
   */
  @Produces
  KeycloakBuilder builder() {
    return KeycloakBuilder.builder();
  }
}
