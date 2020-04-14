package de.untitledrpgwebapp.user.impl.quarkus.testfixture;

import java.util.UUID;

public class UserBoundaryFixture {
  public static final UUID CORRELATION_ID = UUID.randomUUID();
  public static final String NAME = "name";
  public static final String EMAIL = "email";
  public static final String PASSWORD = "password";

  public static final String SERVER_URL = "serverUrl";
  public static final String REALM_NAME = "realmName";
  public static final String ADMIN_CLI_ID = "adminCliId";
  public static final String ADMIN_CLI_SECRET = "adminCliSecret";
}