package de.untitledrpgwebapp.user.impl.quarkus.boundary.testfixture;

import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_EMAIL;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_TWO_EMAIL;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_TWO_NAME;

import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.UserDto;
import java.util.List;

public class UserDtoFixture {
  public static final List<UserDto> DTOS = List.of(
      UserDto.builder().name(USER_ONE_NAME).email(USER_ONE_EMAIL).build(),
      UserDto.builder().name(USER_TWO_NAME).email(USER_TWO_EMAIL).build());

  private UserDtoFixture() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
