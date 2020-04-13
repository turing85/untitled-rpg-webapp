package de.untitledrpgwebapp.language.impl.localstore.testfixture;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.List;
import java.util.UUID;

public class LanguageData {

  public static final UUID CORRELATION_ID = UUID.randomUUID();
  public static final String TAG_ONE = "tagOne";
  public static final String TAG_TWO = "tagTwo";

  public static final LanguageResponse RESPONSE_ONE =
      LanguageResponse.builder().tag(TAG_ONE).build();
  public static final LanguageResponse RESPONSE_TWO =
      LanguageResponse.builder().tag(TAG_TWO).build();
  public static final List<LanguageResponse> LANGUAGES_RESPONSE =
      List.of(RESPONSE_ONE, RESPONSE_TWO);
}
