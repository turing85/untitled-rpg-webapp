package de.untitledrpgwebapp.common.configuration.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
import de.untitledrpgwebapp.common.configuration.ThreadLocalContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.MultivaluedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for LogContainerRequestFilter unit.")
class LogContainerRequestFilterTest extends LoggerTestData {

  private final LogContainerRequestFilter uut = new LogContainerRequestFilter(logger);

  @BeforeEach
  void setup() {
    containerRequestHeadersWithCorrelationId = new MultivaluedHashMap<>();
    containerRequestHeadersWithCorrelationId
        .put(StaticConfig.CORRELATION_ID_HEADER_KEY, List.of(REQUEST_CORRELATION_ID));

    when(requestUriInfo.getRequestUri()).thenReturn(REQUEST_URI);
    when(containerRequest.getMethod()).thenReturn(REQUEST_METHOD);
    when(containerRequest.getUriInfo()).thenReturn(requestUriInfo);
    when(containerRequest.getCookies()).thenReturn(requestCookies);
    when(containerRequest.getEntityStream()).thenReturn(new ByteArrayInputStream(ENTITY.getBytes()));
    when(logger.isInfoEnabled()).thenReturn(true);
    ThreadLocalContext.get().setCorrelationId(null);
  }

  @Test
  @DisplayName("Should call logger with the expected parameters when a container request is "
      + "logged.")
  void shouldCallLoggerWithExpectedParameterWhenContainerRequestIsLogged() throws IOException {
    // GIVEN
    when(containerRequest.getHeaders()).thenReturn(containerRequestHeadersWithCorrelationId);

    // WHEN
    uut.filter(containerRequest);

    // THEN
    verify(logger).isInfoEnabled();
    verify(logger).logRequest(argThat(logObject -> {
      assertThat(logObject.getVerb(), is("Receiving"));
      assertThat(logObject.getRequestMethod(), is(REQUEST_METHOD));
      assertThat(logObject.getAdjective(), is("on"));
      assertThat(logObject.getRequestUri(), is(REQUEST_URI));
      assertThat(logObject.getRequestCorrelationId(), is(REQUEST_CORRELATION_ID));
      assertThat(logObject.getHeaders(), is(containerRequestHeadersWithCorrelationId));
      assertThat(logObject.isRequestCorrelationIdCreated(), is(false));
      return true;
    }));
    assertThat(ThreadLocalContext.get().getCorrelationId(), is(REQUEST_CORRELATION_ID));
  }

  @Test
  @DisplayName("Should add correlation-id and call logger with the expected parameters when a "
      + "container request is logged.")
  void shouldAddCorrelationIdCallLoggerWithExpectedParameterWhenContainerRequestIsLogged()
      throws IOException {
    // GIVEN
    when(containerRequest.getHeaders()).thenReturn(containerRequestHeadersWithoutCorrelationId);

    // WHEN
    uut.filter(containerRequest);

    // THEN
    verify(logger).isInfoEnabled();
    verify(logger).logRequest(argThat(logObject -> {
      assertThat(logObject.getVerb(), is("Receiving"));
      assertThat(logObject.getRequestMethod(), is(REQUEST_METHOD));
      assertThat(logObject.getAdjective(), is("on"));
      assertThat(logObject.getRequestUri(), is(REQUEST_URI));
      assertThat(logObject.getRequestCorrelationId(), is(notNullValue()));
      assertThat(logObject.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY), hasSize(1));
      assertThat(logObject.isRequestCorrelationIdCreated(), is(true));
      return true;
    }));
    assertFalse(ThreadLocalContext.get().getCorrelationId().isBlank());
  }

  @Test
  @DisplayName("Should not log container requests when info is disabled.")
  void shouldNotLogContainerRequestsWhenInfoIsDisabled() throws IOException {
    // GIVEN
    when(containerRequest.getHeaders()).thenReturn(containerRequestHeadersWithoutCorrelationId);
    when(logger.isInfoEnabled()).thenReturn(false);

    // WHEN
    uut.filter(containerRequest);

    // THEN
    verify(logger).isInfoEnabled();
    verifyNoMoreInteractions(logger);
    assertFalse(ThreadLocalContext.get().getCorrelationId().isBlank());
  }
}