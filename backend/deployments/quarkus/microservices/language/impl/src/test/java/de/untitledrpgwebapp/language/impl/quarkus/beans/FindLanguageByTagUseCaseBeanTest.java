package de.untitledrpgwebapp.language.impl.quarkus.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import de.untitledrpgwebapp.language.impl.localstore.domain.FindLanguageByTagInDatabaseUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindLanguageByTagUseCaseBean unit")
class FindLanguageByTagUseCaseBeanTest {

  @Test
  @DisplayName("Should create a FindLanguageByTagInDatabaseUseCase with the expected repository")
  void shouldCreateExpectedFindLanguageByTagUseCase() {
    // GIVEN
    final LanguageRepository repository = mock(LanguageRepository.class);

    // WHEN
    FindLanguageByTagUseCase created = new FindLanguageByTagUseCaseBean().findLanguage(repository);

    // THEN
    assertThat(created, instanceOf(FindLanguageByTagInDatabaseUseCase.class));
    FindLanguageByTagInDatabaseUseCase actual = (FindLanguageByTagInDatabaseUseCase) created;
    assertThat(actual.getRepository(), sameInstance(repository));
  }
}