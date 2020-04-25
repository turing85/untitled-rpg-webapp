package de.untitledrpgwebapp.user.bean.proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;

@DisplayName("Tests for CreateAccountUseCaseProxy unit.")
class CreateAccountUseCaseProxyTest {

  @Test
  @DisplayName("Should create a CreateAccountUseCaseProxy with the expected settings.")
  void shouldCreateExpectedCreateAccountUseCase() {
    // GIVEN
    Keycloak keycloak = mock(Keycloak.class);
    String realmName = "realmName";

    // WHEN
    CreateAccountUseCaseProxy actual = new CreateAccountUseCaseProxy(keycloak, realmName);

    // THEN
    assertThat(actual.getKeycloak(), sameInstance(keycloak));
    assertThat(actual.getRealmName(), is(realmName));
  }

}