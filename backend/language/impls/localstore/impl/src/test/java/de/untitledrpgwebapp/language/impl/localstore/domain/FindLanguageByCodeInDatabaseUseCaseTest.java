package de.untitledrpgwebapp.language.impl.localstore.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindLanguageByCodeFromDatabaseUseCase unit")
class FindLanguageByCodeInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with expected parameters and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    String code = "code";
    final UUID correlationId = UUID.randomUUID();
    FindLanguageByCodeRequest request = FindLanguageByCodeRequest.builder()
        .code(code)
        .correlationId(correlationId)
        .build();

    LanguageRepository repository = mock(LanguageRepository.class);
    when(repository.findByCode(any())).thenReturn(Optional.of(LanguageResponse.builder().build()));

    // WHEN
    Optional<LanguageResponse> fetched =
        new FindLanguageByCodeInDatabaseUseCase(repository).execute(request);

    // THEN
    assertTrue(fetched.isPresent());
    assertEquals(correlationId, fetched.get().getCorrelationId());

    verify(repository).findByCode(code);
  }
}