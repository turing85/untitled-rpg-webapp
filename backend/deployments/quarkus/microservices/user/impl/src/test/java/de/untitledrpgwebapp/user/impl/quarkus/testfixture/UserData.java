package de.untitledrpgwebapp.user.impl.quarkus.testfixture;

import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.UserDto;
import java.util.List;
import java.util.UUID;

public class UserData {
  public static final UUID CORRELATION_ID = UUID.randomUUID();
  public static final String USER_ONE_NAME = "userOneName";
  public static final String USER_TWO_NAME = "userTwoName";
  public static final List<String> USER_NAMES = List.of(USER_ONE_NAME, USER_TWO_NAME);

  public static final List<UserResponse> FOUND = List.of(
      UserResponse.builder().name(USER_ONE_NAME).build(),
      UserResponse.builder().name(USER_TWO_NAME).build());

  public static final List<UserDto> DTOS = List.of(
      UserDto.builder().name(USER_ONE_NAME).build(),
      UserDto.builder().name(USER_TWO_NAME).build());
}
