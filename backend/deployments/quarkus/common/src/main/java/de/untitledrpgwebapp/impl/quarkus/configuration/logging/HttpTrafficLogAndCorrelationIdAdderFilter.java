package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class HttpTrafficLogAndCorrelationIdAdderFilter
    implements ContainerRequestFilter, ContainerResponseFilter, ClientRequestFilter,
    ClientResponseFilter {

  private final Logger logger;
  private final ObjectMapper objectMapper;

  public HttpTrafficLogAndCorrelationIdAdderFilter() {
    logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    objectMapper = new ObjectMapper().setSerializationInclusion(Include.NON_EMPTY);
  }

  @Override
  public void filter(ContainerRequestContext request) throws IOException {
    boolean correlationIdAdded = addCorrelationIdIfNotPresent(
        request.getHeaders().keySet()::contains,
        request.getHeaders()::putSingle);
    final Map<String, List<String>> originalHeaders = request.getHeaders();
    if (logger.isInfoEnabled()) {
      logRequest(HttpTrafficLogObject.builder()
          .verb("Receiving")
          .requestMethod(request.getMethod())
          .adjective("on")
          .requestUri(request.getUriInfo().getAbsolutePath())
          .requestCorrelationId(
              request.getHeaders().get(StaticConfig.X_CORRELATION_ID_HEADER).get(0))
          .requestCorrelationIdAdded(correlationIdAdded)
          .headers(originalHeaders)
          .requestCookies(request.getCookies().values())
          .entity(extractEntityFromStreamAndResetStream(
              request::getEntityStream,
              request::setEntityStream))
          .build());
    }
  }

  @Override
  public void filter(ContainerRequestContext request, ContainerResponseContext response)
      throws JsonProcessingException {
    String requestCorrelationId =
        request.getHeaderString(StaticConfig.X_CORRELATION_ID_HEADER);
    MultivaluedMap<String, Object> headers = response.getHeaders();
    boolean responseCorrelationIdCopied = copyCorrelationIdToObjectHeadersIfNecessary(
        headers.keySet()::contains,
        requestCorrelationId,
        request.getHeaders()::putSingle);
    String responseCorrelationId =
        response.getHeaders().getFirst(StaticConfig.X_CORRELATION_ID_HEADER).toString();
    logResponse(HttpTrafficLogObject.builder()
        .verb("Sending")
        .responseStatus(response.getStatus())
        .responseCorrelationId(responseCorrelationId)
        .responseCorrelationIdCopied(responseCorrelationIdCopied)
        .adjective("on")
        .requestMethod(request.getMethod())
        .requestUri(request.getUriInfo().getAbsolutePath())
        .requestCorrelationId(requestCorrelationId)
        .headers(convertToMapStringListOfString(headers))
        .responseCookies(response.getCookies().values())
        .entity(response.getEntity())
        .build());
  }

  @Override
  public void filter(ClientRequestContext request) throws IOException {
    boolean correlationIdAdded = addCorrelationIdIfNotPresent(
        request.getHeaders().keySet()::contains,
        request.getHeaders()::putSingle);
    Map<String, List<String>> transformedHeaders =
        convertToMapStringListOfString(request.getHeaders());
    logRequest(HttpTrafficLogObject.builder()
        .verb("Sending")
        .requestCorrelationIdAdded(correlationIdAdded)
        .requestMethod(request.getMethod())
        .adjective("to")
        .requestUri(request.getUri())
        .requestCorrelationId(transformedHeaders.get(StaticConfig.X_CORRELATION_ID_HEADER).get(0))
        .headers(obfuscateHeaders(transformedHeaders))
        .requestCookies(obfuscateCookies(request.getCookies().values()))
        .entity(Optional.ofNullable(request.getEntity()).orElse(Collections.emptyList()))
        .build());
  }

  @Override
  public void filter(ClientRequestContext request, ClientResponseContext response)
      throws IOException {
    String requestCorrelationId =
        Optional.ofNullable(request.getHeaders().getFirst(StaticConfig.X_CORRELATION_ID_HEADER))
            .map(Object::toString)
            .orElse("");
    boolean responseCorrelationIdCopied = copyCorrelationIdToObjectHeadersIfNecessary(
        response.getHeaders().keySet()::contains,
        requestCorrelationId,
        response.getHeaders()::putSingle);
    String responseCorrelationId =
        response.getHeaderString(StaticConfig.X_CORRELATION_ID_HEADER);
    logResponse(HttpTrafficLogObject.builder()
        .verb("Receiving")
        .responseStatus(response.getStatus())
        .responseCorrelationId(responseCorrelationId)
        .responseCorrelationIdCopied(responseCorrelationIdCopied)
        .requestMethod(request.getMethod())
        .adjective("to")
        .requestUri(request.getUri())
        .requestCorrelationId(requestCorrelationId)
        .headers(response.getHeaders())
        .responseCookies(response.getCookies().values())
        .entity(extractEntityFromStreamAndResetStream(
            response::getEntityStream,
            response::setEntityStream))
        .build());
  }

  private boolean copyCorrelationIdToObjectHeadersIfNecessary(
      Predicate<String> containsKey,
      String correlationId,
      BiConsumer<String, ? super String> entrySetter) {
    if (!containsKey.test(StaticConfig.X_CORRELATION_ID_HEADER) && Objects.nonNull(correlationId)) {
      entrySetter.accept(StaticConfig.X_CORRELATION_ID_HEADER, correlationId);
      return true;
    }
    return false;
  }

  private boolean addCorrelationIdIfNotPresent(
      Predicate<String> containsKey,
      BiConsumer<String, String> entrySetter) {
    if (!containsKey.test(StaticConfig.X_CORRELATION_ID_HEADER)) {
      entrySetter.accept(StaticConfig.X_CORRELATION_ID_HEADER, UUID.randomUUID().toString());
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
        StaticConfig.OBFUSCATION_VALUE,
        StaticConfig.OBFUSCATION_VALUE,
        cookie.getVersion());
  }

  private Map<String, List<String>> obfuscateHeaders(Map<String, List<String>> headers) {
    Map<String, List<String>> obfuscated = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
      String headerKey = entry.getKey();
      final List<String> headerValues = entry.getValue();
      if (StaticConfig.headersToAnonymize.contains(headerKey)) {
        obfuscated.put(headerKey, List.of(StaticConfig.OBFUSCATION_VALUE));
      } else {
        obfuscated.put(headerKey, headerValues);
      }
    }
    return obfuscated;
  }

  private Object extractEntityFromStreamAndResetStream(
      Supplier<InputStream> inputStreamGetter,
      Consumer<InputStream> inputStreamSetter) throws IOException {
    ByteArrayOutputStream entityRecord = getReplicaOutputStream(inputStreamGetter.get());
    inputStreamSetter.accept(new ByteArrayInputStream(entityRecord.toByteArray()));
    String entityString = new String(entityRecord.toByteArray());
    if (!entityString.isBlank()) {
      return entityString;
    }
    return Collections.emptyList();
  }

  private ByteArrayOutputStream getReplicaOutputStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream replicaOutputStream = new ByteArrayOutputStream();
    while ((inputStream.available() > 0)) {
      replicaOutputStream.writeBytes(inputStream.readNBytes(StaticConfig.CHUNK_SIZE_TO_READ));
    }
    return replicaOutputStream;
  }

  private void logRequest(HttpTrafficLogObject logObject) throws JsonProcessingException {
    if (logger.isInfoEnabled()) {
      logger.info(
          "{} {} request {} {} with {} {}{}. Headers: {}. Cookies: {}. Body: {}",
          logObject.getVerb(),
          logObject.getRequestMethod(),
          logObject.getAdjective(),
          logObject.getRequestUri(),
          StaticConfig.X_CORRELATION_ID_HEADER,
          logObject.getRequestCorrelationId(),
          logObject.isRequestCorrelationIdAdded() ? " (added)" : "",
          objectMapper.writeValueAsString(obfuscateHeaders(logObject.getHeaders())),
          objectMapper.writeValueAsString(obfuscateCookies(logObject.getRequestCookies())),
          objectMapper.writeValueAsString(logObject.getEntity()));
    }
  }

  private Map<String, List<String>> convertToMapStringListOfString(Map<String, List<Object>> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            entry -> entry.getValue().stream()
                .map(Object::toString)
                .collect(Collectors.toList())));
  }

  private Collection<NewCookie> obfuscateNewCookies(Collection<NewCookie> newCookies) {
    List<NewCookie> obfuscated = new ArrayList<>();
    for (final NewCookie cookie : newCookies) {
      final String cookieName = cookie.getName();
      if (StaticConfig.cookiesToAnonymize.contains(cookieName)) {
        obfuscated.add(copyNewCookieWithObfuscatedValue(cookie));
      } else {
        obfuscated.add(cookie);
      }
    }
    return obfuscated;
  }

  private NewCookie copyNewCookieWithObfuscatedValue(NewCookie cookie) {
    return new NewCookie(
        cookie.getName(),
        StaticConfig.OBFUSCATION_VALUE,
        StaticConfig.OBFUSCATION_VALUE,
        StaticConfig.OBFUSCATION_VALUE,
        cookie.getVersion(),
        StaticConfig.OBFUSCATION_VALUE,
        cookie.getMaxAge(),
        cookie.getExpiry(),
        cookie.isSecure(),
        cookie.isHttpOnly());
  }

  private void logResponse(HttpTrafficLogObject logObject)
      throws JsonProcessingException {
    if (logger.isInfoEnabled()) {
      Object responseCorrelationId = logObject.getResponseCorrelationId();
      Object requestCorrelationId = logObject.getRequestCorrelationId();
      int responseStatus = logObject.getResponseStatus();
      logger.info(
          "{} response {} ({}) with {} {}{} for {} request {} {} with {} {}. Headers: {}. "
              + "Cookies: {}. Body: {}",
          logObject.getVerb(),
          responseStatus,
          Response.Status.fromStatusCode(responseStatus),
          Objects.nonNull(responseCorrelationId) ? StaticConfig.X_CORRELATION_ID_HEADER : "no",
          Objects.nonNull(responseCorrelationId)
              ? responseCorrelationId : StaticConfig.X_CORRELATION_ID_HEADER,
          logObject.isResponseCorrelationIdCopied() ? " (copied from request)" : "",
          logObject.getRequestMethod(),
          logObject.getAdjective(),
          logObject.getRequestUri(),
          Objects.nonNull(requestCorrelationId) ? StaticConfig.X_CORRELATION_ID_HEADER : "no",
          Objects.nonNull(requestCorrelationId)
              ? requestCorrelationId : StaticConfig.X_CORRELATION_ID_HEADER,
          objectMapper.writeValueAsString(obfuscateHeaders(logObject.getHeaders())),
          objectMapper.writeValueAsString(obfuscateNewCookies(logObject.getResponseCookies())),
          objectMapper.writeValueAsString(logObject.getEntity()));
    }
  }
}
