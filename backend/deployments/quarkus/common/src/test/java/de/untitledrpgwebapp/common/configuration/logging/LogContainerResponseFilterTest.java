package de.untitledrpgwebapp.common.configuration.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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

@DisplayName("Tests for LogContainerResponseFilter unit.")
class LogContainerResponseFilterTest extends LoggerTestData {

  private final LogContainerResponseFilter uut = new LogContainerResponseFilter(logger);

  @BeforeEach
  void setup() {
    containerRequestHeadersWithCorrelationId = new MultivaluedHashMap<>();
    containerResponseHeadersWithCorrelationId = new MultivaluedHashMap<>();
    containerRequestHeadersWithCorrelationId
        .put(StaticConfig.CORRELATION_ID_HEADER_KEY, List.of(REQUEST_CORRELATION_ID));
    containerResponseHeadersWithCorrelationId
        .put(StaticConfig.CORRELATION_ID_HEADER_KEY, List.of(RESPONSE_CORRELATION_ID));
    when(containerResponse.getEntityStream()).thenReturn(new ByteArrayOutputStream());
    when(containerResponse.getStatus()).thenReturn(RESPONSE_STATUS);
    when(containerResponse.getCookies()).thenReturn(responseCookies);

    when(requestUriInfo.getRequestUri()).thenReturn(REQUEST_URI);
    when(containerRequest.getMethod()).thenReturn(REQUEST_METHOD);
    when(containerRequest.getUriInfo()).thenReturn(requestUriInfo);
    when(containerRequest.getCookies()).thenReturn(requestCookies);
    when(containerRequest.getEntityStream()).thenReturn(new ByteArrayInputStream(ENTITY.getBytes()));
    when(logger.isInfoEnabled()).thenReturn(true);
    ThreadLocalContext.get().setCorrelationId(RESPONSE_CORRELATION_ID);
  }

  @Test
  @DisplayName("Should call logger with the expected parameters when a container response is "
      + "logged.")
  void shouldCallLoggerWithExpectedParameterWhenContainerResponseIsLogged() throws IOException {
    // GIVEN
    when(containerRequest.getHeaders()).thenReturn(containerRequestHeadersWithCorrelationId);
    when(containerResponse.getHeaders()).thenReturn(containerResponseHeadersWithCorrelationId);

    // WHEN
    uut.filter(containerRequest, containerResponse);

    // THEN
    verify(logger).isInfoEnabled();
    verify(logger).logResponse(argThat(logObject -> {
      assertThat(logObject.getVerb(), is("Sending"));
      assertThat(logObject.getResponseStatus(), is(RESPONSE_STATUS));
      assertThat(logObject.getResponseCorrelationId(), is(RESPONSE_CORRELATION_ID));
      assertThat(logObject.isResponseCorrelationIdCopied(), is(false));
      assertThat(logObject.getAdjective(), is("on"));
      assertThat(logObject.getRequestMethod(), is(REQUEST_METHOD));
      assertThat(logObject.getRequestUri(), is(REQUEST_URI));
      assertThat(logObject.getRequestCorrelationId(), is(REQUEST_CORRELATION_ID));
      assertThat(logObject.getHeaders(), is(containerResponseHeadersWithCorrelationId));
      return true;
    }));
  }

  @Test
  @DisplayName("Should copy correlation id from request and call logger with the expected "
      + "parameters when a container response is logged.")
  void shouldCopyCorrelationIdFromRequestAndCallLoggerWithExpectedParameterWhenContainerResponseIsLogged()
      throws IOException {
    // GIVEN
    when(containerRequest.getHeaders()).thenReturn(containerRequestHeadersWithCorrelationId);
    when(containerResponse.getHeaders()).thenReturn(containerResponseHeadersWithoutCorrelationId);

    // WHEN
    uut.filter(containerRequest, containerResponse);

    // THEN
    verify(logger).isInfoEnabled();
    verify(logger).logResponse(argThat(logObject -> {
      assertThat(logObject.getVerb(), is("Sending"));
      assertThat(logObject.getResponseStatus(), is(RESPONSE_STATUS));
      assertThat(logObject.getResponseCorrelationId(), is(REQUEST_CORRELATION_ID));
      assertThat(logObject.isResponseCorrelationIdCopied(), is(true));
      assertThat(logObject.getAdjective(), is("on"));
      assertThat(logObject.getRequestMethod(), is(REQUEST_METHOD));
      assertThat(logObject.getRequestUri(), is(REQUEST_URI));
      assertThat(logObject.getRequestCorrelationId(), is(REQUEST_CORRELATION_ID));
      return true;
    }));
  }

  @Test
  @DisplayName("Should not log container responses when info is disabled.")
  void shouldNotLogContainerResponsesWhenInfoIsDisabled() throws IOException {
    // GIVEN
    when(containerRequest.getHeaders()).thenReturn(containerRequestHeadersWithoutCorrelationId);
    when(containerResponse.getHeaders()).thenReturn(containerResponseHeadersWithoutCorrelationId);
    when(logger.isInfoEnabled()).thenReturn(false);

    // WHEN
    uut.filter(containerRequest, containerResponse);

    // THEN
    verify(logger).isInfoEnabled();
    verifyNoMoreInteractions(logger);
  }
}