package de.untitledrpgwebapp.imp.quarkus.configuration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

  private final Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    final MultivaluedMap<String, String> originalHeaders = requestContext.getHeaders();
    boolean correlationIdAdded = addCorrelationIdIfNotPresent(originalHeaders);
    MultivaluedMap<String, String> headers = anonymizeHeaders(originalHeaders);
    Collection<Cookie> cookies = obfuscateCookies(requestContext.getCookies().values());
    String body = extractEntityFromRequestContext(requestContext);
    logger.info(
        "Received request {} on {} with {} {} {}. Headers: {}. Cookies: {}. Body: {}",
        requestContext.getMethod(),
        requestContext.getUriInfo().getAbsolutePath(),
        StaticConfig.X_CORRELATION_ID_HEADER,
        headers.get(StaticConfig.X_CORRELATION_ID_HEADER).get(0),
        correlationIdAdded ? "(added)" : "",
        headers,
        cookies,
        body);
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
    logger.info("beep");
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

  @Override
  public void filter(
      ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) {
    final MultivaluedMap<String, String> originalHeaders =
        convertToMultiValuedHMapStringString(responseContext.getHeaders());
    MultivaluedMap<String, String> headers = anonymizeHeaders(originalHeaders);
    Collection<NewCookie> newCookies = obfuscateNewCookies(responseContext.getCookies().values());
    logger.info(
        "Returning Response {} to request on {} with {} {}. Headers: {}. Cookies: {}. Body: {}",
        responseContext.getStatus(),
        requestContext.getUriInfo().getAbsolutePath(),
        StaticConfig.X_CORRELATION_ID_HEADER,
        headers.get(StaticConfig.X_CORRELATION_ID_HEADER).get(0),
        headers,
        newCookies,
        responseContext.getEntity());
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
}
