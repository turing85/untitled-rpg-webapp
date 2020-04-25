package de.untitledrpgwebapp.common.configuration.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
import de.untitledrpgwebapp.common.configuration.ThreadLocalContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.MultivaluedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for LogClientResponseFilter unit.")
class LogClientResponseFilterTest extends LoggerTestData {

  private final LogClientResponseFilter uut = new LogClientResponseFilter(logger);

  @BeforeEach
  void setup() {
    clientRequestHeadersWithCorrelationId = new MultivaluedHashMap<>();
    clientResponseHeadersWithCorrelationId = new MultivaluedHashMap<>();
    clientRequestHeadersWithCorrelationId
        .put(StaticConfig.CORRELATION_ID_HEADER_KEY, List.of(REQUEST_CORRELATION_ID));
    clientResponseHeadersWithCorrelationId
        .put(StaticConfig.CORRELATION_ID_HEADER_KEY, List.of(RESPONSE_CORRELATION_ID));

    when(requestUriInfo.getRequestUri()).thenReturn(REQUEST_URI);
    when(logger.isInfoEnabled()).thenReturn(true);

    when(clientResponse.getEntityStream())
        .thenReturn(new ByteArrayInputStream(ENTITY.getBytes()));
    when(clientResponse.getStatus()).thenReturn(RESPONSE_STATUS);
    when(clientResponse.getCookies()).thenReturn(responseCookies);

    when(clientRequest.getMethod()).thenReturn(REQUEST_METHOD);
    when(clientRequest.getUri()).thenReturn(REQUEST_URI);
    when(clientRequest.getCookies()).thenReturn(requestCookies);
    when(clientRequest.getEntityStream()).thenReturn(new ByteArrayOutputStream());
    ThreadLocalContext.get().setCorrelationId(null);
  }

  @Test
  @DisplayName("Should call logger with the expected parameters when a client response is logged.")
  void shouldCallLoggerWithExpectedParameterWhenClientResponseIsLogged() throws IOException {
    // GIVEN
    when(clientRequest.getHeaders()).thenReturn(clientRequestHeadersWithCorrelationId);
    when(clientResponse.getHeaders()).thenReturn(clientResponseHeadersWithCorrelationId);

    // WHEN
    uut.filter(clientRequest, clientResponse);

    // THEN
    verify(logger).isInfoEnabled();
    verify(logger).logResponse(argThat(logObject -> {
      assertThat(logObject.getVerb(), is("Receiving"));
      assertThat(logObject.getResponseStatus(), is(RESPONSE_STATUS));
      assertThat(logObject.getResponseCorrelationId(), is(RESPONSE_CORRELATION_ID));
      assertThat(logObject.isResponseCorrelationIdCopied(), is(false));
      assertThat(logObject.getAdjective(), is("to"));
      assertThat(logObject.getRequestMethod(), is(REQUEST_METHOD));
      assertThat(logObject.getRequestUri(), is(REQUEST_URI));
      assertThat(logObject.getRequestCorrelationId(), is(REQUEST_CORRELATION_ID));
      assertThat(logObject.getHeaders(), is(clientResponseHeadersWithCorrelationId));
      return true;
    }));
    assertThat(ThreadLocalContext.get().getCorrelationId(), is(RESPONSE_CORRELATION_ID));
  }

  @Test
  @DisplayName("Should copy correlation id from request and call logger with the expected "
      + "parameters when a client response is logged.")
  void shouldCopyCorrelationIdFromRequestAndCallLoggerWithExpectedParameterWhenClientResponseIsLogged()
      throws IOException {
    // GIVEN
    when(clientRequest.getHeaders()).thenReturn(clientRequestHeadersWithCorrelationId);
    when(clientResponse.getHeaders()).thenReturn(clientResponseHeadersWithoutCorrelationId);

    // WHEN
    uut.filter(clientRequest, clientResponse);

    // THEN
    verify(logger).isInfoEnabled();
    verify(logger).logResponse(argThat(logObject -> {
      assertThat(logObject.getVerb(), is("Receiving"));
      assertThat(logObject.getResponseStatus(), is(RESPONSE_STATUS));
      assertThat(logObject.getResponseCorrelationId(), is(REQUEST_CORRELATION_ID));
      assertThat(logObject.isResponseCorrelationIdCopied(), is(true));
      assertThat(logObject.getAdjective(), is("to"));
      assertThat(logObject.getRequestMethod(), is(REQUEST_METHOD));
      assertThat(logObject.getRequestUri(), is(REQUEST_URI));
      assertThat(logObject.getRequestCorrelationId(), is(REQUEST_CORRELATION_ID));
      assertThat(logObject.getHeaders(), is(clientResponseHeadersWithoutCorrelationId));
      return true;
    }));
    assertThat(ThreadLocalContext.get().getCorrelationId(), is(REQUEST_CORRELATION_ID));
  }

  @Test
  @DisplayName("Should not log client responses when info is disabled.")
  void shouldNotLogClientResponsesWhenInfoIsDisabled() throws IOException {
    // GIVEN
    when(clientRequest.getHeaders()).thenReturn(clientRequestHeadersWithoutCorrelationId);
    when(clientResponse.getHeaders()).thenReturn(clientResponseHeadersWithoutCorrelationId);
    when(logger.isInfoEnabled()).thenReturn(false);

    // WHEN
    uut.filter(clientRequest, clientResponse);

    // THEN
    verify(logger).isInfoEnabled();
    verifyNoMoreInteractions(logger);
    assertTrue(ThreadLocalContext.get().getCorrelationId().isBlank());
  }

}