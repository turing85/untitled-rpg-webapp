package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import java.io.IOException;
import java.util.Objects;
import javax.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class logs http requests and responses.
 *
 * <p>Headers, cookies and the body are obfuscated before being logged by using the {@link
 * LogObfuscator}.
 *
 * @see LogObfuscator
 */
@Getter(AccessLevel.PACKAGE)
public class HttpTrafficLogger {

  public static final String LOG_REQUEST_FORMAT_TEMPLATE =
      "{} {} request {} {} with {} {}{}. Headers: {}. Cookies: {}. Body: {}";
  public static final String LOG_RESPONSE_FORMAT_TEMPLATE =
      "{} response {} ({}) with {} {}{} for {} request {} {} with {} {}. Headers: {}. Cookies: {}. "
          + "Body: {}";

  final Logger logger;
  final LogObfuscator obfuscator;

  public HttpTrafficLogger() {
    this(LoggerFactory.getLogger(HttpTrafficLogger.class), new LogObfuscator());
  }

  HttpTrafficLogger(Logger logger, LogObfuscator obfuscator) {
    this.logger = logger;
    this.obfuscator = obfuscator;
  }

  void logRequest(HttpTrafficLogObject logObject) throws IOException {
    if (logger.isInfoEnabled()) {
      logger.info(
          LOG_REQUEST_FORMAT_TEMPLATE,
          logObject.getVerb(),
          logObject.getRequestMethod(),
          logObject.getAdjective(),
          logObject.getRequestUri(),
          StaticConfig.CORRELATION_ID_LOG_NAME,
          logObject.getRequestCorrelationId(),
          logObject.isRequestCorrelationIdCreated() ? " (created by filter)" : "",
          obfuscator.obfuscateHeaders(logObject.getHeaders()),
          obfuscator.obfuscateCookies(logObject.getRequestCookies()),
          obfuscator.obfuscateEntityString(logObject.getEntity()));
    }
  }

  void logResponse(HttpTrafficLogObject logObject) throws IOException {
    if (logger.isInfoEnabled()) {
      Object responseCorrelationId = logObject.getResponseCorrelationId();
      Object requestCorrelationId = logObject.getRequestCorrelationId();
      int responseStatus = logObject.getResponseStatus();
      logger.info(
          LOG_RESPONSE_FORMAT_TEMPLATE,
          logObject.getVerb(),
          responseStatus,
          Response.Status.fromStatusCode(responseStatus),
          Objects.nonNull(responseCorrelationId) ? StaticConfig.CORRELATION_ID_HEADER_KEY : "no",
          Objects.nonNull(responseCorrelationId)
              ? responseCorrelationId
              : StaticConfig.CORRELATION_ID_HEADER_KEY,
          logObject.isResponseCorrelationIdCopied() ? " (copied from request)" : "",
          logObject.getRequestMethod(),
          logObject.getAdjective(),
          logObject.getRequestUri(),
          Objects.nonNull(requestCorrelationId) ? StaticConfig.CORRELATION_ID_HEADER_KEY : "no",
          Objects.nonNull(requestCorrelationId)
              ? requestCorrelationId
              : StaticConfig.CORRELATION_ID_HEADER_KEY,
          obfuscator.obfuscateHeaders(logObject.getHeaders()),
          obfuscator.obfuscateNewCookies(logObject.getResponseCookies()),
          obfuscator.obfuscateEntityString(logObject.getEntity()));
    }
  }

  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }
}
