package de.untitledrpgwebapp.language.impl.quarkus.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import de.untitledrpgwebapp.language.impl.localstore.domain.FindAllLanguagesInDatabaseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindAllLanguagesUseCaseBean unit.")
class FindAllLanguagesUseCaseBeanTest {

  @Test
  void shouldCreateExpectedFindAllLanguagesUseCase() {
    // GIVEN
    LanguageDao dao = mock(LanguageDao.class);

    // WHEN
    FindAllLanguagesUseCase created =
        new FindAllLanguagesUseCaseBean().findAllLanguages(dao);

    // THEN
    assertThat(created, instanceOf(FindAllLanguagesInDatabaseUseCase.class));
    FindAllLanguagesInDatabaseUseCase actual = (FindAllLanguagesInDatabaseUseCase) created;
    assertThat(actual.getDao(), sameInstance(dao));
  }
}