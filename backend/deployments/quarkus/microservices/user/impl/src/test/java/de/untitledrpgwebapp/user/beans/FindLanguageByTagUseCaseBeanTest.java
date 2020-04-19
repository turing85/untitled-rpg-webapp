package de.untitledrpgwebapp.user.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.boundary.mapper.LanguageRestMapper;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagViaRestUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindLanguageByTagUseCaseBean unit.")
class FindLanguageByTagUseCaseBeanTest {

  @Test
  @DisplayName("Should create a FindLanguageByTagUseCase with the expected DAO.")
  void shouldCreateExpectedFindLanguageByTagUseCase() {
    // GIVEN
    LanguageRestClient restClient = mock(LanguageRestClient.class);
    LanguageRestMapper mapper = mock(LanguageRestMapper.class);

    // WHEN
    FindLanguageByTagUseCase created =
        new FindLanguageByTagUseCaseBean().findLanguage(restClient, mapper);

    // THEN
    assertThat(created, instanceOf(FindLanguageByTagViaRestUseCase.class));
    FindLanguageByTagViaRestUseCase actual = (FindLanguageByTagViaRestUseCase) created;
    assertThat(actual.getClient(), sameInstance(restClient));
    assertThat(actual.getMapper(), sameInstance(mapper));
  }

}