package de.untitledrpgwebapp.language.boundary.spi.testfixture;

import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_TWO_NAME;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_TWO_TAG;

import de.untitledrpgwebapp.language.boundary.spi.entity.JpaLanguageEntity;
import java.util.Collection;
import java.util.List;

public class LanguageEntityFixture {
  
  public static final JpaLanguageEntity LANGUAGE_ONE_ENTITY = JpaLanguageEntity.builder()
      .tag(LANGUAGE_ONE_TAG)
      .name(LANGUAGE_TWO_NAME)
      .build();
  public static final JpaLanguageEntity LANGUAGE_TWO_ENTITY = JpaLanguageEntity.builder()
      .tag(LANGUAGE_TWO_TAG)
      .name(LANGUAGE_TWO_NAME)
      .build();
  public static final Collection<JpaLanguageEntity> LANGUAGE_ENTITES =
      List.of(LANGUAGE_ONE_ENTITY, LANGUAGE_TWO_ENTITY);

  private LanguageEntityFixture() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
