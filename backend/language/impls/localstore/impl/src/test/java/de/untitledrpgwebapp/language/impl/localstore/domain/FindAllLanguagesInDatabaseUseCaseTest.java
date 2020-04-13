package de.untitledrpgwebapp.language.impl.localstore.domain;

import static de.untitledrpgwebapp.language.impl.localstore.testfixture.LanguageData.CORRELATION_ID;
import static de.untitledrpgwebapp.language.impl.localstore.testfixture.LanguageData.LANGUAGES_RESPONSE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.request.FindAllLanguagesRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindAllLanguagesInDatabaseUseCase unit")
class FindAllLanguagesInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with the expected parameter and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    FindAllLanguagesRequest request =
        FindAllLanguagesRequest.builder().correlationId(CORRELATION_ID).build();

    LanguageRepository repository = mock(LanguageRepository.class);
    when(repository.findAll()).thenReturn(LANGUAGES_RESPONSE);

    // WHEN
    Collection<LanguageResponse> actual =
        new FindAllLanguagesInDatabaseUseCase(repository).execute(request);

    // THEN
    assertThat(actual, hasSize(LANGUAGES_RESPONSE.size()));
    for (LanguageResponse entry : actual) {
      assertThat(entry.getCorrelationId(), is(CORRELATION_ID));
    }

    verify(repository).findAll();
  }
}