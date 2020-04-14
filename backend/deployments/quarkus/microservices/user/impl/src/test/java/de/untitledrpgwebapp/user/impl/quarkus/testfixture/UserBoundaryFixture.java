package de.untitledrpgwebapp.user.impl.quarkus.testfixture;

import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_EMAIL;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_TWO_EMAIL;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_TWO_NAME;

import de.untitledrpgwebapp.user.impl.quarkus.entity.JpaUserEntity;
import java.util.List;
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

  public static final JpaUserEntity USER_ENTITY_ONE = JpaUserEntity.builder()
      .name(USER_ONE_NAME)
      .email(USER_ONE_EMAIL)
      .build();
  public static final JpaUserEntity USER_ENTITY_TWO = JpaUserEntity.builder()
      .name(USER_TWO_NAME)
      .email(USER_TWO_EMAIL)
      .build();
  public static final List<JpaUserEntity> USER_ENTITIES = List.of(USER_ENTITY_ONE, USER_ENTITY_TWO);
  
}
