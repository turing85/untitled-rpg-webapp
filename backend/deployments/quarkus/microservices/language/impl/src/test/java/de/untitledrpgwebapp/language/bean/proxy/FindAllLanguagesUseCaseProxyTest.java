package de.untitledrpgwebapp.language.bean.proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindAllLanguagesUseCaseProxy unit.")
class FindAllLanguagesUseCaseProxyTest {

  @Test
  @DisplayName("Should create a FindAllLanguagesUseCaseProxy with the expected DAO.")
  void shouldCreateExpectedFindAllLanguagesUseCase() {
    // GIVEN
    LanguageDao dao = mock(LanguageDao.class);

    // WHEN
    FindAllLanguagesUseCaseProxy actual = new FindAllLanguagesUseCaseProxy(dao);

    // THEN
    assertThat(actual.getDao(), sameInstance(dao));
  }
}