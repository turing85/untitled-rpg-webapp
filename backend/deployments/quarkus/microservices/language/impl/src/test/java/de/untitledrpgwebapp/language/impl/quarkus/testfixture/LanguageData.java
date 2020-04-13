package de.untitledrpgwebapp.language.impl.quarkus.testfixture;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.LanguageDto;
import java.util.List;
import java.util.UUID;

public class LanguageData {

  public static final UUID CORRELATION_ID = UUID.randomUUID();
  public static final String LANGUAGE_ONE_TAG = "languageOneTag";
  public static final String LANGUAGE_TWO_TAG = "languageTwoTag";
  public static final List<String> LANGUAGE_TAGS = List.of(LANGUAGE_ONE_TAG, LANGUAGE_TWO_TAG);

  public static final List<LanguageResponse> FOUND = List.of(
      LanguageResponse.builder().tag(LANGUAGE_ONE_TAG).build(),
      LanguageResponse.builder().tag(LANGUAGE_TWO_TAG).build());

  public static final List<LanguageDto> DTOS = List.of(
      LanguageDto.builder().tag(LANGUAGE_ONE_TAG).build(),
      LanguageDto.builder().tag(LANGUAGE_TWO_TAG).build());
}
