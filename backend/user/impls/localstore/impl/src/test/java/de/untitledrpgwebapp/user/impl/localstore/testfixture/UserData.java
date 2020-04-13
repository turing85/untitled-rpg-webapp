package de.untitledrpgwebapp.user.impl.localstore.testfixture;

import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.List;
import java.util.UUID;

public class UserData {
  public static final UUID CORRELATION_ID = UUID.randomUUID();
  public static final String NAME_ONE = "nameOne";
  public static final String EMAIL_ONE = "emailOne";
  public static final String TAG_ONE = "tagOne";
  public static final String NAME_TWO = "nameTwo";
  public static final String EMAIL_TWO = "emailTwo";
  public static final String TAG_TWO = "tagTwo";

  public static final UserResponse RESPONSE_ONE =
      UserResponse.builder().name(NAME_ONE).email(EMAIL_ONE).preferredLanguageTag(TAG_ONE).build();
  public static final UserResponse RESPONSE_TWO =
      UserResponse.builder().name(NAME_TWO).email(EMAIL_TWO).preferredLanguageTag(TAG_TWO).build();
  
  public static final List<UserResponse> RESPONSES = List.of(RESPONSE_ONE, RESPONSE_TWO);
}
