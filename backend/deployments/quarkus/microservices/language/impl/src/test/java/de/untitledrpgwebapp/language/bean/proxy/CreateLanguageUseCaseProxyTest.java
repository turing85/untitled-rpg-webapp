package de.untitledrpgwebapp.language.bean.proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateLanguageUseCaseProxy unit.")
class CreateLanguageUseCaseProxyTest {

  @Test
  @DisplayName("Should create a CreateLanguageUseCaseProxy with the expected DAO.")
  void shouldCreateExpectedCreateLanguageUseCase() {
    // GIVEN
    LanguageDao dao = mock(LanguageDao.class);

    // WHEN
    CreateLanguageUseCaseProxy actual = new CreateLanguageUseCaseProxy(dao);

    // THEN
    assertThat(actual.getDao(), sameInstance(dao));
  }
}