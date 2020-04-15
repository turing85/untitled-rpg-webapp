package de.untitledrpgwebapp.user.impl.quarkus.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.mapper.LanguageMapper;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.domain.FindLanguageByTagViaRestUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindLanguageByTagUseCaseBean unit.")
class FindLanguageByTagUseCaseBeanTest {

  @Test
  @DisplayName("Should create a FindLanguageByTagUseCase with the expected repository.")
  void shouldCreateExpectedFindLanguageByTagUseCase() {
    // GIVEN
    LanguageRestClient restClient = mock(LanguageRestClient.class);
    LanguageMapper mapper = mock(LanguageMapper.class);

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