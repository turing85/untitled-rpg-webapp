package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("Tests for HttpTrafficLogger unit.")
class HttpTrafficLoggerTest {

  public static final String verb = "verb";
  public static final int responseStatus = 200;
  public static final String responseCorrelationId = "responseCorrelationId";
  public static final String requestMethod = "requestMethod";
  public static final String adjective = "adjective";
  public static final URI requestUri = URI.create("http://localhost");
  public static final String requestCorrelationId = "requestCorrelationId";
  public static final HashMap<String, List<String>> headers = new HashMap<>();
  public static final List<NewCookie> responseCookies = new ArrayList<>();
  public static final List<Cookie> requestCookies = new ArrayList<>();
  public static final String entity = "entity";

  public static final String obfuscatedHeaders = "obfuscatedHeaders";
  public static final String obfuscatedResponseCookies = "obfuscatedResponseCookies";
  public static final String obfuscatedRequestCookies = "obfuscatedRequestCookies";
  public static final String obfuscatedEntityString = "obfuscatedBody";

  private final HttpTrafficLogObject logObject = HttpTrafficLogObject.builder()
      .verb(verb)
      .responseStatus(responseStatus)
      .responseCorrelationId(responseCorrelationId)
      .requestMethod(requestMethod)
      .adjective(adjective)
      .requestUri(requestUri)
      .requestCorrelationId(requestCorrelationId)
      .responseCorrelationIdCopied(false)
      .requestCorrelationIdCreated(false)
      .headers(headers)
      .responseCookies(responseCookies)
      .requestCookies(requestCookies)
      .entity(entity)
      .build();

  private final Logger logger = mock(Logger.class);
  private final LogObfuscator obfuscator = mock(LogObfuscator.class);

  private final HttpTrafficLogger uut = new HttpTrafficLogger(logger, obfuscator);

  @BeforeEach
  void setup() throws IOException {
    when(logger.isInfoEnabled()).thenReturn(true);
    when(obfuscator.obfuscateHeaders(any())).thenReturn(obfuscatedHeaders);
    when(obfuscator.obfuscateCookies(any())).thenReturn(obfuscatedRequestCookies);
    when(obfuscator.obfuscateNewCookies(any())).thenReturn(obfuscatedResponseCookies);
    when(obfuscator.obfuscateEntityString(anyString())).thenReturn(obfuscatedEntityString);
  }

  @Test
  @DisplayName("Should initialize with correct logger when default constructor is called.")
  void shouldInitializeWithCorrectLoggerWhenDefaultConstructorIsCalled() {
    // GIVEN: nothing

    // WHEN
    HttpTrafficLogger logger = new HttpTrafficLogger();

    // THEN
    assertTrue(uut.isInfoEnabled());

    assertThat(logger.getLogger(), is(LoggerFactory.getLogger(HttpTrafficLogger.class)));
    assertThat(logger.getObfuscator(), is(notNullValue()));
  }

  @Test
  @DisplayName("Should log expected message when logRequest is called.")
  void shouldLogExpectedMessageWhenLogRequestIsCalled() throws IOException {
    // GIVEN
    logObject.setRequestCorrelationIdCreated(false);

    // WHEN
    uut.logRequest(logObject);

    // THEN
    assertTrue(uut.isInfoEnabled());

    verify(logger, times(2)).isInfoEnabled();
    verify(logger).info(
        HttpTrafficLogger.LOG_REQUEST_FORMAT_TEMPLATE,
        logObject.getVerb(),
        logObject.getRequestMethod(),
        logObject.getAdjective(),
        logObject.getRequestUri(),
        StaticConfig.CORRELATION_ID_LOG_NAME,
        logObject.getRequestCorrelationId(),
        "",
        obfuscatedHeaders,
        obfuscatedRequestCookies,
        obfuscatedEntityString);
  }

  @Test
  @DisplayName("Should log message with \"(created by filter)\" when logRequest is called.")
  void shouldLogExpectedMessageWithCreatedByFilterWhenLogRequestIsCalled() throws IOException {
    // GIVEN
    logObject.setRequestCorrelationIdCreated(true);

    // WHEN
    uut.logRequest(logObject);

    // THEN
    assertTrue(uut.isInfoEnabled());

    verify(logger, times(2)).isInfoEnabled();
    verify(logger).info(
        HttpTrafficLogger.LOG_REQUEST_FORMAT_TEMPLATE,
        logObject.getVerb(),
        logObject.getRequestMethod(),
        logObject.getAdjective(),
        logObject.getRequestUri(),
        StaticConfig.CORRELATION_ID_LOG_NAME,
        logObject.getRequestCorrelationId(),
        " (created by filter)",
        obfuscatedHeaders,
        obfuscatedRequestCookies,
        obfuscatedEntityString);
  }

  @Test
  @DisplayName("should not log any request when info is not enabled.")
  void shouldNotLogRequestsWhenInfoIsNotEnabled() throws IOException {
    // GIVEN
    when(logger.isInfoEnabled()).thenReturn(false);

    // WHEN
    uut.logRequest(logObject);

    // THEN
    assertFalse(uut.isInfoEnabled());

    verify(logger, times(2)).isInfoEnabled();
    verifyNoMoreInteractions(logger);
  }

  @Test
  @DisplayName("Should log with correlationId and request with correlationId.")
  void shouldLogResponseWithCorrelationIdAndRequestWithCorrelationId() throws IOException {
    // GIVEN
    logObject.setResponseCorrelationIdCopied(false);

    // WHEN
    uut.logResponse(logObject);

    // THEN
    assertTrue(uut.isInfoEnabled());

    verify(logger, times(2)).isInfoEnabled();
    verify(logger).info(
        HttpTrafficLogger.LOG_RESPONSE_FORMAT_TEMPLATE,
        logObject.getVerb(),
        logObject.getResponseStatus(),
        Response.Status.fromStatusCode(logObject.getResponseStatus()),
        StaticConfig.CORRELATION_ID_HEADER_KEY,
        logObject.getResponseCorrelationId(),
        "",
        logObject.getRequestMethod(),
        logObject.getAdjective(),
        logObject.getRequestUri(),
        StaticConfig.CORRELATION_ID_HEADER_KEY,
        logObject.getRequestCorrelationId(),
        obfuscatedHeaders,
        obfuscatedResponseCookies,
        obfuscatedEntityString);
  }

  @Test
  @DisplayName("Should log with no correlationId and request with correlationId.")
  void shouldLogResponseWithNoCorrelationIdAndRequestWithCorrelationId() throws IOException {
    // GIVEN
    logObject.setResponseCorrelationId(null);

    // WHEN
    uut.logResponse(logObject);

    // THEN
    assertTrue(uut.isInfoEnabled());

    verify(logger, times(2)).isInfoEnabled();
    verify(logger).info(
        HttpTrafficLogger.LOG_RESPONSE_FORMAT_TEMPLATE,
        logObject.getVerb(),
        logObject.getResponseStatus(),
        Response.Status.fromStatusCode(logObject.getResponseStatus()),
        "no",
        StaticConfig.CORRELATION_ID_HEADER_KEY,
        "",
        logObject.getRequestMethod(),
        logObject.getAdjective(),
        logObject.getRequestUri(),
        StaticConfig.CORRELATION_ID_HEADER_KEY,
        logObject.getRequestCorrelationId(),
        obfuscatedHeaders,
        obfuscatedResponseCookies,
        obfuscatedEntityString);
  }

  @Test
  @DisplayName("Should log with copied correlationId and request with no correlationId.")
  void shouldLogResponseWithCopiedCorrelationIdAndRequestWithCorrelationId() throws IOException {
    // GIVEN
    logObject.setResponseCorrelationIdCopied(true);

    // WHEN
    uut.logResponse(logObject);

    // THEN
    assertTrue(uut.isInfoEnabled());

    verify(logger, times(2)).isInfoEnabled();
    verify(logger).info(
        HttpTrafficLogger.LOG_RESPONSE_FORMAT_TEMPLATE,
        logObject.getVerb(),
        logObject.getResponseStatus(),
        Response.Status.fromStatusCode(logObject.getResponseStatus()),
        StaticConfig.CORRELATION_ID_HEADER_KEY,
        logObject.getResponseCorrelationId(),
        " (copied from request)",
        logObject.getRequestMethod(),
        logObject.getAdjective(),
        logObject.getRequestUri(),
        StaticConfig.CORRELATION_ID_HEADER_KEY,
        logObject.getRequestCorrelationId(),
        obfuscatedHeaders,
        obfuscatedResponseCookies,
        obfuscatedEntityString);
  }

  @Test
  @DisplayName("Should log with correlationId and request with no correlationId.")
  void shouldLogResponseWithCorrelationIdAndRequestWithNoCorrelationId() throws IOException {
    // GIVEN
    logObject.setResponseCorrelationIdCopied(false);
    logObject.setRequestCorrelationId(null);

    // WHEN
    uut.logResponse(logObject);

    // THEN
    assertTrue(uut.isInfoEnabled());

    verify(logger, times(2)).isInfoEnabled();
    verify(logger).info(
        HttpTrafficLogger.LOG_RESPONSE_FORMAT_TEMPLATE,
        logObject.getVerb(),
        logObject.getResponseStatus(),
        Response.Status.fromStatusCode(logObject.getResponseStatus()),
        StaticConfig.CORRELATION_ID_HEADER_KEY,
        logObject.getResponseCorrelationId(),
        "",
        logObject.getRequestMethod(),
        logObject.getAdjective(),
        logObject.getRequestUri(),
        "no",
        StaticConfig.CORRELATION_ID_HEADER_KEY,
        obfuscatedHeaders,
        obfuscatedResponseCookies,
        obfuscatedEntityString);
  }

  @Test
  @DisplayName("should not log any responses when info is not enabled.")
  void shouldNotLogResponsesWhenInfoIsNotEnabled() throws IOException {
    // GIVEN
    when(logger.isInfoEnabled()).thenReturn(false);

    // WHEN
    uut.logResponse(logObject);

    // THEN
    assertFalse(uut.isInfoEnabled());

    verify(logger, times(2)).isInfoEnabled();
    verifyNoMoreInteractions(logger);
  }
}