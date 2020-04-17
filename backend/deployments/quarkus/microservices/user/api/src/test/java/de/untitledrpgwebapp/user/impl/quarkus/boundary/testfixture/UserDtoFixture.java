package de.untitledrpgwebapp.user.impl.quarkus.boundary.testfixture;

import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_EMAIL;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_TWO_EMAIL;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_TWO_NAME;

import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.UserDto;
import java.util.List;

public class UserDtoFixture {

  public static final UserDto USER_DTO_ONE =
      UserDto.builder().name(USER_ONE_NAME).email(USER_ONE_EMAIL).build();
  public static final UserDto USER_DTO_TWO =
      UserDto.builder().name(USER_TWO_NAME).email(USER_TWO_EMAIL).build();
  public static final List<UserDto> USER_DTOS = List.of(
      USER_DTO_ONE,
      USER_DTO_TWO);

  private UserDtoFixture() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
