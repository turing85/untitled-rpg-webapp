package de.untitledrpgwebapp.language.impl.localstore.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindLanguageByTagInDatabaseUseCase unit")
class FindLanguageByTagInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with expected parameters and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    String tag = "en_US";
    final UUID correlationId = UUID.randomUUID();
    FindLanguageByTagRequest request = FindLanguageByTagRequest.builder()
        .tag(tag)
        .correlationId(correlationId)
        .build();

    LanguageRepository repository = mock(LanguageRepository.class);
    when(repository.findByTag(any())).thenReturn(Optional.of(LanguageResponse.builder().build()));

    // WHEN
    Optional<LanguageResponse> fetched =
        new FindLanguageByTagInDatabaseUseCase(repository).execute(request);

    // THEN
    assertTrue(fetched.isPresent());
    assertThat(fetched.get().getCorrelationId(), is(correlationId));

    verify(repository).findByTag(tag);
  }
}