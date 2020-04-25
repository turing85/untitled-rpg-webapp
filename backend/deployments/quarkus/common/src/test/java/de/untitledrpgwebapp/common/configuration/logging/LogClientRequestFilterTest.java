package de.untitledrpgwebapp.common.configuration.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
import de.untitledrpgwebapp.common.configuration.ThreadLocalContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.MultivaluedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for LogClientRequestFilter unit.")
class LogClientRequestFilterTest extends LoggerTestData {

  private final LogClientRequestFilter uut = new LogClientRequestFilter(logger);

  @BeforeEach
  void setup() {
    clientRequestHeadersWithCorrelationId = new MultivaluedHashMap<>();
    clientRequestHeadersWithCorrelationId
        .put(StaticConfig.CORRELATION_ID_HEADER_KEY, List.of(REQUEST_CORRELATION_ID));

    when(requestUriInfo.getRequestUri()).thenReturn(REQUEST_URI);
    when(logger.isInfoEnabled()).thenReturn(true);

    when(clientRequest.getMethod()).thenReturn(REQUEST_METHOD);
    when(clientRequest.getUri()).thenReturn(REQUEST_URI);
    when(clientRequest.getCookies()).thenReturn(requestCookies);
    when(clientRequest.getEntityStream()).thenReturn(new ByteArrayOutputStream());
    ThreadLocalContext.get().setCorrelationId(null);
  }

  @Test
  @DisplayName("Should call logger with the expected parameters when a client request is "
      + "logged.")
  void shouldCallLoggerWithExpectedParameterWhenClientRequestIsLogged() throws IOException {
    // GIVEN
    when(clientRequest.getHeaders()).thenReturn(clientRequestHeadersWithCorrelationId);

    // WHEN
    uut.filter(clientRequest);

    // THEN
    verify(logger).isInfoEnabled();
    verify(logger).logRequest(argThat(logObject -> {
      assertThat(logObject.getVerb(), is("Sending"));
      assertThat(logObject.getRequestMethod(), is(REQUEST_METHOD));
      assertThat(logObject.getAdjective(), is("to"));
      assertThat(logObject.getRequestUri(), is(REQUEST_URI));
      assertThat(logObject.getRequestCorrelationId(), is(REQUEST_CORRELATION_ID));
      assertThat(logObject.getHeaders(), is(clientRequestHeadersWithCorrelationId));
      assertThat(logObject.isRequestCorrelationIdCreated(), is(false));
      return true;
    }));
    assertThat(ThreadLocalContext.get().getCorrelationId(), is(REQUEST_CORRELATION_ID));
  }

  @Test
  @DisplayName("Should add correlation-id and call logger with the expected parameters when a "
      + "client request is logged.")
  void shouldAddCorrelationIdCallLoggerWithExpectedParameterWhenClientRequestIsLogged()
      throws IOException {
    // GIVEN
    when(clientRequest.getHeaders()).thenReturn(clientRequestHeadersWithoutCorrelationId);

    // WHEN
    uut.filter(clientRequest);

    // THEN
    verify(logger).isInfoEnabled();
    verify(logger).logRequest(argThat(logObject -> {
      assertThat(logObject.getVerb(), is("Sending"));
      assertThat(logObject.getRequestMethod(), is(REQUEST_METHOD));
      assertThat(logObject.getAdjective(), is("to"));
      assertThat(logObject.getRequestUri(), is(REQUEST_URI));
      assertThat(logObject.getRequestCorrelationId(), is(notNullValue()));
      assertThat(logObject.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY), hasSize(1));
      assertThat(logObject.isRequestCorrelationIdCreated(), is(true));
      return true;
    }));
    assertThat(ThreadLocalContext.get().getCorrelationId(), is(notNullValue()));
  }

  @Test
  @DisplayName("Should not log client requests when info is disabled.")
  void shouldNotLogClientRequestsWhenInfoIsDisabled() throws IOException {
    // GIVEN
    when(clientRequest.getHeaders()).thenReturn(clientRequestHeadersWithoutCorrelationId);
    when(logger.isInfoEnabled()).thenReturn(false);

    // WHEN
    uut.filter(clientRequest);

    // THEN
    verify(logger).isInfoEnabled();
    verifyNoMoreInteractions(logger);
    assertThat(ThreadLocalContext.get().getCorrelationId(), is(notNullValue()));
  }

}