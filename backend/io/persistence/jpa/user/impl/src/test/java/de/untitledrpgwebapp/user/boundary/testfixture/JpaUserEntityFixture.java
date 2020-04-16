package de.untitledrpgwebapp.user.boundary.testfixture;

import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_EMAIL;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_TWO_EMAIL;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_TWO_NAME;

import de.untitledrpgwebapp.user.boundary.spi.entity.JpaUserEntity;
import java.util.List;

public class JpaUserEntityFixture {
  public static final JpaUserEntity USER_ENTITY_ONE = JpaUserEntity.builder()
      .name(USER_ONE_NAME)
      .email(USER_ONE_EMAIL)
      .build();
  public static final JpaUserEntity USER_ENTITY_TWO = JpaUserEntity.builder()
      .name(USER_TWO_NAME)
      .email(USER_TWO_EMAIL)
      .build();
  public static final List<JpaUserEntity> USER_ENTITIES = List.of(USER_ENTITY_ONE, USER_ENTITY_TWO);

  private JpaUserEntityFixture () {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
