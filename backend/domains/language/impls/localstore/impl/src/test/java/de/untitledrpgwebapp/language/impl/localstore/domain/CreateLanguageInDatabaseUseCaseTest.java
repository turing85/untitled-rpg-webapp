package de.untitledrpgwebapp.language.impl.localstore.domain;

import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_RESPONSE;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateLanguageInDatabaseUseCase unit")
class CreateLanguageInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with the expected parameter and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    CreateLanguageRequest request = CreateLanguageRequest.builder()
        .tag(LANGUAGE_ONE_TAG)
        .correlationId(CORRELATION_ID)
        .build();
    LanguageRepository repository = mock(LanguageRepository.class);
    when(repository.save(any())).thenReturn(LANGUAGE_ONE_RESPONSE);

    // WHEN
    LanguageResponse actual = new CreateLanguageInDatabaseUseCase(repository).execute(request);

    // THEN
    assertThat(actual.getTag(), is(LANGUAGE_ONE_TAG));
    assertThat(actual.getCorrelationId(), is(CORRELATION_ID));

    verify(repository).save(request);
  }
}