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

  @Produces
  public CreateAccountUseCase createAccount(
      Keycloak keycloak,
      @ConfigProperty(name = "oauth2.realm.name") String realmName) {
    return new KeycloakCreateAccountUseCase(
        keycloak,
        realmName,
        Mappers.getMapper(AccountMapper.class));
  }

  @Produces
  public Keycloak keycloak(
      @ConfigProperty(name = "oauth2.server-url") String serverUrl,
      @ConfigProperty(name = "oauth2.realm.name") String realmName,
      @ConfigProperty(name = "oauth2.admin-cli.id") String adminCliId,
      @ConfigProperty(name = "oauth2.admin-cli.secret") String adminCliSecret) {
    return KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realmName)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .clientId(adminCliId)
        .clientSecret(adminCliSecret)
        .build();
  }
}
