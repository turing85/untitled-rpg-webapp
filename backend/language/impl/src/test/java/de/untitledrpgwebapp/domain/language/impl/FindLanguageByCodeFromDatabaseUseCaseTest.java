package de.untitledrpgwebapp.domain.language.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.boundary.language.LanguageRepository;
import de.untitledrpgwebapp.boundary.language.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindLanguageByCodeFromDatabaseUseCaseImplTest unit")
class FindLanguageByCodeFromDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with expected parameters and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    String code = "code";
    UUID correlationId = UUID.randomUUID();
    FindLanguageByCodeRequest request = mock(FindLanguageByCodeRequest.class);
    when(request.getCode()).thenReturn(code);
    when(request.getCorrelationId()).thenReturn(correlationId);

    LanguageResponseBuilder responseBuilder = mock(LanguageResponseBuilder.class);
    when(responseBuilder.setCorrelationId(any())).thenReturn(responseBuilder);
    LanguageRepository languageRepository = mock(LanguageRepository.class);
    when(languageRepository.findByCode(anyString())).thenReturn(Optional.of(responseBuilder));

    // WHEN
    Optional<LanguageResponseBuilder> fetched =
        new FindLanguageByCodeFromDatabaseUseCase(languageRepository).execute(request);

    // THEN
    assertTrue(fetched.isPresent());
    LanguageResponseBuilder actual = fetched.get();
    assertSame(responseBuilder, actual);

    verify(languageRepository).findByCode(code);
  }

}