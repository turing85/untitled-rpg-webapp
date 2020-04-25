package de.untitledrpgwebapp.user.bean.proxy;

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

@DisplayName("Tests for FindLanguageByTagUseCaseProxy unit.")
class FindLanguageByTagUseCaseProxyTest {

  @Test
  @DisplayName("Should create a FindLanguageByTagUseCaseProxy with the expected settings.")
  void shouldCreateExpectedFindLanguageByTagUseCase() {
    // GIVEN
    LanguageRestClient restClient = mock(LanguageRestClient.class);
    LanguageRestMapper mapper = mock(LanguageRestMapper.class);

    // WHEN
    FindLanguageByTagUseCaseProxy actual =
        new FindLanguageByTagUseCaseProxy(restClient, mapper);

    // THEN
    assertThat(actual.getClient(), sameInstance(restClient));
    assertThat(actual.getMapper(), sameInstance(mapper));
  }
}