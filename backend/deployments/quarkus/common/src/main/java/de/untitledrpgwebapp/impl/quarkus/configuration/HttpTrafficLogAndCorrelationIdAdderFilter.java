package de.untitledrpgwebapp.impl.quarkus.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class HttpTrafficLogAndCorrelationIdAdderFilter
    implements ContainerRequestFilter, ContainerResponseFilter {

  private final Logger logger;
  private final ObjectMapper objectMapper;

  public HttpTrafficLogAndCorrelationIdAdderFilter() {
    logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    objectMapper = new ObjectMapper();
  }

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    final MultivaluedMap<String, String> originalHeaders = requestContext.getHeaders();
    boolean correlationIdAdded = addCorrelationIdIfNotPresent(originalHeaders);
    if (logger.isInfoEnabled()) {
      MultivaluedMap<String, String> headers = anonymizeHeaders(originalHeaders);
      Collection<Cookie> cookies = obfuscateCookies(requestContext.getCookies().values());
      String body = extractEntityFromRequestContext(requestContext);
      logRequest(requestContext, correlationIdAdded, headers, cookies, body);
    }
  }

  @Override
  public void filter(ContainerRequestContext request, ContainerResponseContext response)
      throws JsonProcessingException {
    String requestCorrelationId =
        request.getHeaderString(StaticConfig.X_CORRELATION_ID_HEADER);
    copyCorrelationIdFromRequestToResponseIfNecessary(response, requestCorrelationId);
    String responseCorrelationId =
        response.getHeaderString(StaticConfig.X_CORRELATION_ID_HEADER);
    if (logger.isInfoEnabled()) {
      final MultivaluedMap<String, String> originalHeaders =
          convertToMultiValuedHMapStringString(response.getHeaders());
      MultivaluedMap<String, String> headers = anonymizeHeaders(originalHeaders);
      Collection<NewCookie> cookies = obfuscateNewCookies(response.getCookies().values());
      logResponse(request, requestCorrelationId, response, responseCorrelationId, headers, cookies);
    }
  }

  private void copyCorrelationIdFromRequestToResponseIfNecessary(
      ContainerResponseContext responseContext,
      String requestCorrelationId) {
    String responseCorrelationId =
        responseContext.getHeaderString(StaticConfig.X_CORRELATION_ID_HEADER);
    if (Objects.isNull(responseCorrelationId) && Objects.nonNull(requestCorrelationId)) {
      responseContext.getHeaders()
          .add(StaticConfig.X_CORRELATION_ID_HEADER, requestCorrelationId);
    }
  }

  private boolean addCorrelationIdIfNotPresent(MultivaluedMap<String, String> headers) {
    if (!headers.containsKey(StaticConfig.X_CORRELATION_ID_HEADER)) {
      headers.put(StaticConfig.X_CORRELATION_ID_HEADER, List.of(UUID.randomUUID().toString()));
      return true;
    }
    return false;
  }

  private Collection<Cookie> obfuscateCookies(Collection<Cookie> cookies) {
    List<Cookie> anonymize = new ArrayList<>();
    for (final Cookie cookie : cookies) {
      final String cookieName = cookie.getName();
      if (StaticConfig.cookiesToAnonymize.contains(cookieName)) {
        anonymize.add(copyCookieWithObfuscatedValue(cookie));
      } else {
        anonymize.add(cookie);
      }
    }
    return anonymize;
  }

  private Cookie copyCookieWithObfuscatedValue(Cookie cookie) {
    return new Cookie(
        cookie.getName(),
        StaticConfig.OBFUSCATION_VALUE,
        cookie.getPath(),
        cookie.getDomain(),
        cookie.getVersion());
  }

  private MultivaluedMap<String, String> anonymizeHeaders(MultivaluedMap<String, String> headers) {
    MultivaluedMap<String, String> anonymize = new MultivaluedHashMap<>();
    for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
      String headerKey = entry.getKey();
      final List<String> headerValues = entry.getValue();
      if (StaticConfig.headersToAnonymize.contains(headerKey)) {
        List<String> anonymizeHeaderValues = headerValues.stream()
            .map(value -> StaticConfig.OBFUSCATION_VALUE)
            .collect(Collectors.toList());
        anonymize.put(headerKey, anonymizeHeaderValues);
      } else {
        anonymize.put(headerKey, headerValues);
      }
    }
    return anonymize;
  }

  private String extractEntityFromRequestContext(
      ContainerRequestContext requestContext) throws IOException {
    ByteArrayOutputStream entityRecord = getReplicaOutputStream(requestContext.getEntityStream());
    requestContext.setEntityStream(new ByteArrayInputStream(entityRecord.toByteArray()));
    String entityAsString = new String(entityRecord.toByteArray());
    if (entityAsString.isBlank()) {
      return StaticConfig.EMPTY_BODY;
    }
    return entityAsString;
  }

  private ByteArrayOutputStream getReplicaOutputStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream replicaOutputStream = new ByteArrayOutputStream();
    while ((inputStream.available() > 0)) {
      replicaOutputStream.writeBytes(inputStream.readNBytes(StaticConfig.CHUNK_SIZE_TO_READ));
    }
    return replicaOutputStream;
  }

  private void logRequest(
      ContainerRequestContext requestContext,
      boolean correlationIdAdded,
      MultivaluedMap<String, String> headers, Collection<Cookie> cookies, String body)
      throws JsonProcessingException {
    if (logger.isInfoEnabled()) {
      logger.info(
          "Received request {} on {} with {} {} {}. Headers: {}. Cookies: {}. Body: {}",
          requestContext.getMethod(),
          requestContext.getUriInfo().getAbsolutePath(),
          StaticConfig.X_CORRELATION_ID_HEADER,
          headers.get(StaticConfig.X_CORRELATION_ID_HEADER).get(0),
          correlationIdAdded ? "(added)" : "",
          headers,
          cookies,
          objectMapper.writeValueAsString(body));
    }
  }

  private MultivaluedMap<String, String> convertToMultiValuedHMapStringString(
      MultivaluedMap<String, Object> map) {
    MultivaluedHashMap<String, String> converted = new MultivaluedHashMap<>();
    map.forEach((key, value) -> converted.put(
        key,
        value.stream()
            .map(Object::toString)
            .collect(Collectors.toList())));
    return converted;
  }

  private Collection<NewCookie> obfuscateNewCookies(Collection<NewCookie> newCookies) {
    List<NewCookie> anonymize = new ArrayList<>();
    for (final NewCookie cookie : newCookies) {
      final String cookieName = cookie.getName();
      if (StaticConfig.cookiesToAnonymize.contains(cookieName)) {
        anonymize.add(copyNewCookieWithObfuscatedValue(cookie));
      } else {
        anonymize.add(cookie);
      }
    }
    return anonymize;
  }

  private NewCookie copyNewCookieWithObfuscatedValue(NewCookie cookie) {
    return new NewCookie(
        cookie.getName(),
        StaticConfig.OBFUSCATION_VALUE,
        cookie.getPath(),
        cookie.getDomain(),
        cookie.getVersion(),
        cookie.getComment(),
        cookie.getMaxAge(),
        cookie.getExpiry(),
        cookie.isSecure(),
        cookie.isHttpOnly());
  }

  private void logResponse(
      ContainerRequestContext request,
      String requestCorrelationId,
      ContainerResponseContext response,
      String responseCorrelationId,
      MultivaluedMap<String, String> headers,
      Collection<NewCookie> cookies) throws JsonProcessingException {
    if (logger.isInfoEnabled()) {
      logger.info(
          "Sending response {} with {} {} to request on {} with {} {}. Headers: {}. Cookies: {}. "
              + "Body: {}",
          response.getStatus(),
          Objects.nonNull(responseCorrelationId) ? StaticConfig.X_CORRELATION_ID_HEADER : "no",
          Objects.nonNull(responseCorrelationId)
              ? responseCorrelationId : StaticConfig.X_CORRELATION_ID_HEADER,
          request.getUriInfo().getAbsolutePath(),
          Objects.nonNull(requestCorrelationId) ? StaticConfig.X_CORRELATION_ID_HEADER : "no",
          Objects.nonNull(requestCorrelationId)
              ? requestCorrelationId : StaticConfig.X_CORRELATION_ID_HEADER,
          headers,
          cookies,
          objectMapper.writeValueAsString(response.getEntity()));
    }
  }
}
