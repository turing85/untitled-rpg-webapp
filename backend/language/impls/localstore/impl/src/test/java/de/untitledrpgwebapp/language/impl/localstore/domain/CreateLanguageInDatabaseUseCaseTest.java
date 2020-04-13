package de.untitledrpgwebapp.language.impl.localstore.domain;

import static de.untitledrpgwebapp.language.impl.localstore.testfixture.LanguageData.CORRELATION_ID;
import static de.untitledrpgwebapp.language.impl.localstore.testfixture.LanguageData.RESPONSE_ONE;
import static de.untitledrpgwebapp.language.impl.localstore.testfixture.LanguageData.TAG_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
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
        .tag(TAG_ONE)
        .correlationId(CORRELATION_ID)
        .build();
    LanguageRepository repository = mock(LanguageRepository.class);
    when(repository.save(any())).thenReturn(RESPONSE_ONE);

    // WHEN
    LanguageResponse actual = new CreateLanguageInDatabaseUseCase(repository).execute(request);

    // THEN
    assertResponseIsAsExpected(actual);
    verifyRepositoryWasCalledWithExpectedParameters(repository);
  }

  private void assertResponseIsAsExpected(LanguageResponse response) {
    assertThat(response.getTag(), is(TAG_ONE));
    assertThat(response.getCorrelationId(), is(CORRELATION_ID));
  }

  private void verifyRepositoryWasCalledWithExpectedParameters(LanguageRepository repository) {
    verify(repository).save(argThat(r -> {
      assertThat(r.getTag(), is(TAG_ONE));
      assertThat(r.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
  }
}