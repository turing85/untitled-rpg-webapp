package de.untitledrpgwebapp.user.testfixture;

import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.*;

import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserFixture {
  public static final UUID CORRELATION_ID = UUID.randomUUID();
  public static final String USER_ONE_NAME = "userOneName";
  public static final String USER_ONE_EMAIL = "userOneEmail";
  public static final String USER_TWO_NAME = "userTwoName";
  public static final String USER_TWO_EMAIL = "userTwoEmail";

  public static final UserResponse USER_RESPONSE_ONE = UserResponse.builder()
      .name(USER_ONE_NAME)
      .email(USER_ONE_EMAIL)
      .preferredLanguageTag(LANGUAGE_ONE_TAG)
      .correlationId(CORRELATION_ID)
      .build();
  public static final UserResponse USER_RESPONSE_TWO = UserResponse.builder()
      .name(USER_TWO_NAME)
      .email(USER_TWO_EMAIL)
      .correlationId(CORRELATION_ID)
      .build();
  public static final List<UserResponse> USER_RESPONSES =
      List.of(USER_RESPONSE_ONE, USER_RESPONSE_TWO);

  public static final List<String> USER_NAMES = USER_RESPONSES.stream()
      .map(UserResponse::getName)
      .collect(Collectors.toList());

  private UserFixture() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
