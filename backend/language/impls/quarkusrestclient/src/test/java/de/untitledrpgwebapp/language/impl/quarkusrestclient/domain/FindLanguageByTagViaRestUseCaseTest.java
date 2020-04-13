package de.untitledrpgwebapp.language.impl.quarkusrestclient.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.LanguageDto;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.mapper.LanguageMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindLanguageByTagViaRestUseCase unit")
class FindLanguageByTagViaRestUseCaseTest {

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when everything is ok.")
  void shouldCallDependenciesWithExpectedParametersWhenEverythingIsOkay() {
    // GIVEN
    String tag = "tag";
    UUID correlationId = UUID.randomUUID();
    FindLanguageByTagRequest request = FindLanguageByTagRequest.builder()
        .tag(tag)
        .correlationId(correlationId)
        .build();
    LanguageRestClient client = mock(LanguageRestClient.class);
    LanguageDto dto = LanguageDto.builder().build();
    when(client.findByTag(any(), any())).thenReturn(Optional.of(dto));

    LanguageResponse response = LanguageResponse.builder().tag(tag).build();
    LanguageMapper mapper = mock(LanguageMapper.class);
    when(mapper.dtoToResponse(any(), any())).thenReturn(response);

    // WHEN
    Optional<LanguageResponse> fetched =
        new FindLanguageByTagViaRestUseCase(client, mapper).execute(request);

    // THEN
    assertTrue(fetched.isPresent());
    assertThat(fetched.get(), sameInstance(response));

    verify(client).findByTag(tag, correlationId);
    verify(mapper).dtoToResponse(dto, correlationId);
  }
}