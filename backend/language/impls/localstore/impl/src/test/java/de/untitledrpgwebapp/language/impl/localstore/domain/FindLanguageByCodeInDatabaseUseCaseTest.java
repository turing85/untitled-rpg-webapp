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
    String name = "name";
    UUID correlationId = UUID.randomUUID();
    FindLanguageByCodeRequest request = FindLanguageByCodeRequest.builder()
        .code(code)
        .correlationId(correlationId)
        .build();
    LanguageResponse fromDatabase = LanguageResponse.builder()
        .code(code)
        .name(name)
        .build();
    LanguageRepository repository = mock(LanguageRepository.class);
    when(repository.findByCode(any())).thenReturn(Optional.of(fromDatabase));

    // WHEN
    Optional<LanguageResponse> fetched =
        new FindLanguageByCodeInDatabaseUseCase(repository).execute(request);

    // THEN
    assertTrue(fetched.isPresent());
    LanguageResponse actual = fetched.get();
    assertEquals(code, actual.getCode());
    assertEquals(name, actual.getName());
    assertEquals(correlationId, actual.getCorrelationId());

    verify(repository).findByCode(code);
  }

}