package de.untitledrpgwebapp.language.impl.quarkus.boundary.testfixture;

import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_NAME;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_TWO_NAME;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_TWO_TAG;

import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.LanguageDto;
import java.util.Collection;
import java.util.List;

public class LanguageDtoFixture {

  public static final LanguageDto LANGUAGE_ONE_DTO = LanguageDto.builder()
      .tag(LANGUAGE_ONE_TAG)
      .name(LANGUAGE_ONE_NAME)
      .build();
  public static final LanguageDto LANGUAGE_TWO_DTO = LanguageDto.builder()
      .tag(LANGUAGE_TWO_TAG)
      .name(LANGUAGE_TWO_NAME)
      .build();

  public static final Collection<LanguageDto> DTOS = List.of(LANGUAGE_ONE_DTO, LANGUAGE_TWO_DTO);

  private LanguageDtoFixture() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
