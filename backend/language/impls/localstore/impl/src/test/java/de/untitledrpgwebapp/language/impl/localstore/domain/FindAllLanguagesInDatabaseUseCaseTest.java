package de.untitledrpgwebapp.language.impl.localstore.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.request.FindAllLanguagesRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindAllLanguagesInDatabaseUseCase unit")
class FindAllLanguagesInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with the expected parameter and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    final UUID correlationId = UUID.randomUUID();
    FindAllLanguagesRequest request =
        FindAllLanguagesRequest.builder().correlationId(correlationId).build();
    LanguageResponse languageOne = LanguageResponse.builder().build();
    LanguageResponse languageTwo = LanguageResponse.builder().build();
    List<LanguageResponse> response = List.of(languageOne, languageTwo);

    LanguageRepository repository = mock(LanguageRepository.class);
    when(repository.findAll()).thenReturn(response);

    // WHEN
    Collection<LanguageResponse> actual =
        new FindAllLanguagesInDatabaseUseCase(repository).execute(request);

    // THEN
    assertThat(actual, hasSize(response.size()));
    for (LanguageResponse entry : actual) {
      assertEquals(correlationId, entry.getCorrelationId());
    }

    verify(repository).findAll();
  }
}