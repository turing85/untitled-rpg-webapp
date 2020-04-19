package de.untitledrpgwebapp.language.domain;

import static de.untitledrpgwebapp.language.boundary.testfixture.LanguageDtoFixture.LANGUAGE_ONE_DTO;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_RESPONSE;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.boundary.mapper.LanguageRestMapper;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Optional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindLanguageByTagViaRestUseCase unit.")
class FindLanguageByTagViaRestUseCaseTest {

  private final FindLanguageByTagRequest request = FindLanguageByTagRequest.builder()
      .tag(LANGUAGE_ONE_TAG)
      .correlationId(CORRELATION_ID)
      .build();

  private final LanguageRestClient client = mock(LanguageRestClient.class);
  private final LanguageRestMapper mapper = mock(LanguageRestMapper.class);

  private final FindLanguageByTagViaRestUseCase uut =
      new FindLanguageByTagViaRestUseCase(client, mapper);

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when everything is ok.")
  void shouldCallDependenciesWithExpectedParametersWhenEverythingIsOkay() {
    // GIVEN
    when(client.findByTag(any(), any())).thenReturn(Optional.of(LANGUAGE_ONE_DTO));
    when(mapper.dtoToResponse(any(), any())).thenReturn(LANGUAGE_ONE_RESPONSE);

    // WHEN
    Optional<LanguageResponse> fetched = uut.execute(request);

    // THEN
    assertTrue(fetched.isPresent());
    assertThat(fetched.get(), sameInstance(LANGUAGE_ONE_RESPONSE));

    verify(client).findByTag(LANGUAGE_ONE_TAG, CORRELATION_ID);
    verify(mapper).dtoToResponse(LANGUAGE_ONE_DTO, CORRELATION_ID);
  }

  @Test
  @DisplayName("Should return an empty Optional if the rest client throws a "
      + "WebApplicationException with a 404 (NOT FOUND) response code.")
  void shouldReturnEmptyOptionalIfRestClientFailsWithNotFoundError() {
    // GIVEN
    Response response = Response.status(Status.NOT_FOUND.getStatusCode()).build();
    WebApplicationException exception = new WebApplicationException(response);

    when(client.findByTag(anyString(), any())).thenThrow(exception );

    // WHEN
    Optional<LanguageResponse> fetched = uut.execute(request);

    // THEN
    assertFalse(fetched.isPresent());

    verify(client).findByTag(LANGUAGE_ONE_TAG, CORRELATION_ID);
    verifyNoInteractions(mapper);
  }

  @Test
  @DisplayName("Should forward the WebApplicationException if the rest client throws a "
      + "WebApplicationException with a non-404 (NOT FOUND) response code.")
  void shouldThrowWebApplicationExceptionIfRestClientFailsWithNonNotFoundError() {
    // GIVEN
    Response response = Response.status(Status.BAD_REQUEST.getStatusCode()).build();
    WebApplicationException exception = new WebApplicationException(response);

    when(client.findByTag(anyString(), any())).thenThrow(exception);

    // WHEN
    WebApplicationException actual = assertThrows(
        WebApplicationException.class,
        () -> uut.execute(request));

    // THEN
    assertThat(actual, sameInstance(exception));
  }
}