package de.untitledrpgwebapp.language.testfixture;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LanguageFixture {

  public static final String LANGUAGE_ONE_TAG = "languageOneTag";
  public static final String LANGUAGE_ONE_NAME = "languageOneName";
  public static final String LANGUAGE_TWO_TAG = "languageTwoTag";
  public static final String LANGUAGE_TWO_NAME = "languageTwoName";
  public static final UUID CORRELATION_ID = UUID.randomUUID();

  public static final LanguageResponse LANGUAGE_ONE_RESPONSE = LanguageResponse.builder()
      .tag(LANGUAGE_ONE_TAG)
      .name(LANGUAGE_ONE_NAME)
      .correlationId(CORRELATION_ID)
      .build();
  public static final LanguageResponse LANGUAGE_TWO_RESPONSE = LanguageResponse.builder()
      .tag(LANGUAGE_TWO_TAG)
      .name(LANGUAGE_TWO_NAME)
      .correlationId(CORRELATION_ID)
      .build();
  public static final List<LanguageResponse> LANGUAGE_RESPONSES =
      List.of(LANGUAGE_ONE_RESPONSE, LANGUAGE_TWO_RESPONSE);

  public static final List<String> LANGUAGE_TAGS = LANGUAGE_RESPONSES.stream()
      .map(LanguageResponse::getTag)
      .collect(Collectors.toList());

  private LanguageFixture() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
