package de.untitledrpgwebapp.ouath2.impl.keycloak.testfixture;

import java.util.UUID;

public class KeycloakFixture {
  public static final UUID CORRELATION_ID = UUID.randomUUID();
  public static final String NAME = "name";
  public static final String EMAIL = "email";
  public static final String PASSWORD = "password";

  public static final String SERVER_URL = "serverUrl";
  public static final String REALM_NAME = "realmName";
  public static final String ADMIN_CLI_ID = "adminCliId";
  public static final String ADMIN_CLI_SECRET = "adminCliSecret";

  private KeycloakFixture() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
