package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for HttpTrafficLogAndCorrelationIdAdderFilter unit.")
class HttpTrafficLogAndCorrelationIdAdderFilterTest {

  public static final String ENTITY = "entity";
  public static final int RESPONSE_STATUS = 200;
  public static final String RESPONSE_CORRELATION_ID = "responseCorrelationId";
  public static final String REQUEST_METHOD = "requestMethod";
  public static final URI REQUEST_URI = URI.create("http://localhost");
  public static final String REQUEST_CORRELATION_ID = "requestCorrelationId";

  private final HttpTrafficLogger logger = mock(HttpTrafficLogger.class);
  private final ContainerResponseContext containerResponse = mock(ContainerResponseContext.class);
  private final ContainerRequestContext containerRequest = mock(ContainerRequestContext.class);
  private final ClientRequestContext clientRequest = mock(ClientRequestContext.class);
  private final ClientResponseContext clientResponse = mock(ClientResponseContext.class);
  private final UriInfo requestUriInfo = mock(UriInfo.class);
  private final MultivaluedHashMap<String, String> containerRequestHeadersWithoutCorrelationId =
      new MultivaluedHashMap<>();
  private final MultivaluedHashMap<String, Object> containerResponseHeadersWithoutCorrelationId =
      new MultivaluedHashMap<>();
  private final MultivaluedHashMap<String, Object> clientRequestHeadersWithoutCorrelationId =
      new MultivaluedHashMap<>();
  private final MultivaluedHashMap<String, String> clientResponseHeadersWithoutCorrelationId =
      new MultivaluedHashMap<>();
  private final HashMap<String, Cookie> requestCookies = new HashMap<>();
  private final HashMap<String, NewCookie> responseCookies = new HashMap<>();

  private final HttpTrafficLogAndCorrelationIdAdderFilter uut =
      new HttpTrafficLogAndCorrelationIdAdderFilter(logger);

  private MultivaluedHashMap<String, String> containerRequestHeadersWithCorrelationId;
  private MultivaluedHashMap<String, Object> containerResponseHeadersWithCorrelationId;
  private MultivaluedHashMap<String, Object> clientRequestHeadersWithCorrelationId;
  private MultivaluedHashMap<String, String> clientResponseHeadersWithCorrelationId;

  @BeforeEach
  void setup() {
    containerRequestHeadersWithCorrelationId = new MultivaluedHashMap<>();
    containerResponseHeadersWithCorrelationId = new MultivaluedHashMap<>();
    clientRequestHeadersWithCorrelationId = new MultivaluedHashMap<>();
    clientResponseHeadersWithCorrelationId = new MultivaluedHashMap<>();
    containerRequestHeadersWithCorrelationId
        .put(StaticConfig.CORRELATION_ID_HEADER_KEY, List.of(REQUEST_CORRELATION_ID));
    containerResponseHeadersWithCorrelationId
        .put(StaticConfig.CORRELATION_ID_HEADER_KEY, List.of(RESPONSE_CORRELATION_ID));
    clientRequestHeadersWithCorrelationId
        .put(StaticConfig.CORRELATION_ID_HEADER_KEY, List.of(REQUEST_CORRELATION_ID));
    clientResponseHeadersWithCorrelationId
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

    when(clientResponse.getEntityStream())
        .thenReturn(new ByteArrayInputStream(ENTITY.getBytes()));
    when(clientResponse.getStatus()).thenReturn(RESPONSE_STATUS);
    when(clientResponse.getCookies()).thenReturn(responseCookies);

    when(clientRequest.getMethod()).thenReturn(REQUEST_METHOD);
    when(clientRequest.getUri()).thenReturn(REQUEST_URI);
    when(clientRequest.getCookies()).thenReturn(requestCookies);
    when(clientRequest.getEntityStream()).thenReturn(new ByteArrayOutputStream());

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
      assertThat(logObject.getHeaders(), is(containerRequestHeadersWithCorrelationId));
      assertThat(logObject.isRequestCorrelationIdCreated(), is(false));
      return true;
    }));
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
      assertThat(logObject.getHeaders(), is(containerResponseHeadersWithCorrelationId));
      return true;
    }));
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
  }
}