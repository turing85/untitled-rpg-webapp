package de.untitledrpgwebapp.language.impl.localstore.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateLanguageInDatabaseUseCase unit")
class CreateLanguageInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the repository with the expected parameter and return the expected "
      + "response when everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    final String tag = "tag";
    final String name = "name";
    UUID correlationId = UUID.randomUUID();
    CreateLanguageRequest request = CreateLanguageRequest.builder()
        .tag(tag)
        .name(name)
        .correlationId(correlationId)
        .build();
    LanguageResponse response = LanguageResponse.builder().tag(tag).name(name).build();
    LanguageRepository repository = mock(LanguageRepository.class);
    when(repository.save(any())).thenReturn(response);

    // WHEN
    LanguageResponse actual = new CreateLanguageInDatabaseUseCase(repository).execute(request);

    // THEN
    assertEquals(tag, actual.getTag());
    assertEquals(name, actual.getName());
    assertEquals(correlationId, actual.getCorrelationId());

    verify(repository).save(argThat(r -> {
      assertEquals(tag, r.getTag());
      assertEquals(name, r.getName());
      assertEquals(correlationId, r.getCorrelationId());
      return true;
    }));
  }

}