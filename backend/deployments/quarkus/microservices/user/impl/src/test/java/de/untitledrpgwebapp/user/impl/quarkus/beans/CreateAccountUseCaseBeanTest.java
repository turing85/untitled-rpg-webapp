package de.untitledrpgwebapp.user.impl.quarkus.beans;

import static de.untitledrpgwebapp.user.impl.quarkus.testfixture.UserBoundaryFixture.ADMIN_CLI_ID;
import static de.untitledrpgwebapp.user.impl.quarkus.testfixture.UserBoundaryFixture.ADMIN_CLI_SECRET;
import static de.untitledrpgwebapp.user.impl.quarkus.testfixture.UserBoundaryFixture.REALM_NAME;
import static de.untitledrpgwebapp.user.impl.quarkus.testfixture.UserBoundaryFixture.SERVER_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.oauth2.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.ouath2.impl.keycloak.domain.KeycloakCreateAccountUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@DisplayName("Tests for CreateAccountUseCaseBean unit")
class CreateAccountUseCaseBeanTest {

  KeycloakBuilder builder;

  private final CreateAccountUseCaseBean uut = new CreateAccountUseCaseBean();

  private final Keycloak keycloak = KeycloakBuilder.builder()
      .serverUrl(SERVER_URL)
      .realm(REALM_NAME)
      .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
      .clientId(ADMIN_CLI_ID)
      .clientSecret(ADMIN_CLI_SECRET)
      .build();

  @BeforeEach
  void setup() {
    builder = mock(KeycloakBuilder.class);
    when(builder.serverUrl(anyString())).thenReturn(builder);
    when(builder.realm(anyString())).thenReturn(builder);
    when(builder.grantType(any())).thenReturn(builder);
    when(builder.clientId(anyString())).thenReturn(builder);
    when(builder.clientSecret(anyString())).thenReturn(builder);
    when(builder.build()).thenReturn(keycloak);
  }

  @Test
  @DisplayName("Should create a KeycloakCreateAccountUseCase with the expected settings.")
  void shouldCreateExpectedCreateAccountUseCase() {
    // GIVEN: defaults

    // WHEN
    CreateAccountUseCase created = uut.createAccount(keycloak, REALM_NAME);

    // THEN
    assertThat(created, instanceOf(KeycloakCreateAccountUseCase.class));
    KeycloakCreateAccountUseCase actual = (KeycloakCreateAccountUseCase) created;
    assertThat(actual.getKeycloak(), is(keycloak));
    assertThat(actual.getRealmName(), is(REALM_NAME));
  }

  @Test
  @DisplayName("Should create a Keycloak with the expected settings.")
  void shouldCreateExpectedKeycloak() {
    // GIVEN: defaults

    // WHEN
    Keycloak actual = uut.keycloak(builder, SERVER_URL, REALM_NAME, ADMIN_CLI_ID, ADMIN_CLI_SECRET);

    // THEN
    assertThat(actual, is(sameInstance(keycloak)));

    verify(builder).serverUrl(SERVER_URL);
    verify(builder).realm(REALM_NAME);
    verify(builder).grantType(OAuth2Constants.CLIENT_CREDENTIALS);
    verify(builder).clientId(ADMIN_CLI_ID);
    verify(builder).clientSecret(ADMIN_CLI_SECRET);
    verify(builder).build();
  }

  @Test
  @DisplayName("Should create a KeycloakBuilder.")
  void shouldCreateKeycloakBuilder() {
    // GIVEN: nothing

    // WHEN
    KeycloakBuilder actual = uut.builder();

    // THEN
    assertThat(actual, is(notNullValue()));
  }
}