package de.untitledrpgwebapp.language.impl.quarkus.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import de.untitledrpgwebapp.language.impl.localstore.domain.CreateLanguageInDatabaseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateLanguageUseCaseBean unit.")
class CreateLanguageUseCaseBeanTest {

  @Test
  @DisplayName("Should create a CreateLanguageInDatabaseUseCase with the expected repository.")
  void shouldCreateExpectedCreateLanguageUseCase() {
    // GIVEN
    LanguageDao dao = mock(LanguageDao.class);

    // WHEN
    CreateLanguageUseCase created = new CreateLanguageUseCaseBean().createLanguage(dao);

    // THEN
    assertThat(created, instanceOf(CreateLanguageInDatabaseUseCase.class));
    CreateLanguageInDatabaseUseCase actual = (CreateLanguageInDatabaseUseCase) created;
    assertThat(actual.getDao(), sameInstance(dao));
  }
}