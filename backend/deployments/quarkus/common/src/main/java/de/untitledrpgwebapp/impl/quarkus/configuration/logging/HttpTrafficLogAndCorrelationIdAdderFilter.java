package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Provider
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class HttpTrafficLogAndCorrelationIdAdderFilter
    implements ContainerRequestFilter, ContainerResponseFilter, ClientRequestFilter,
    ClientResponseFilter {

  public static final String CORRELATION_ID_HEADER_KEY = StaticConfig.CORRELATION_ID_HEADER_KEY;

  private HttpTrafficLogger logger = new HttpTrafficLogger();

  @Override
  public void filter(ContainerRequestContext request) throws IOException {
    boolean correlationIdAdded = addCorrelationIdIfNotPresent(
        request.getHeaders().keySet()::contains,
        request.getHeaders()::putSingle);

    logRequestToBeReceived(request, correlationIdAdded);
  }

  @Override
  public void filter(ContainerRequestContext request, ContainerResponseContext response)
      throws IOException {
    String requestCorrelationId = extractCorrelationIdStringFromHeader(request.getHeaders());
    MultivaluedMap<String, Object> responseHeaders = response.getHeaders();
    boolean isResponseCorrelationIdCopied = copyCorrelationIdToHeadersIfNotPresent(
        responseHeaders.keySet()::contains,
        requestCorrelationId,
        responseHeaders::putSingle);

    logResponseToBeSent(response, isResponseCorrelationIdCopied, request, requestCorrelationId);
  }

  @Override
  public void filter(ClientRequestContext request) throws IOException {
    boolean correlationIdCreated = addCorrelationIdIfNotPresent(
        request.getHeaders().keySet()::contains,
        request.getHeaders()::putSingle);

    logRequestToBeSent(request, correlationIdCreated);
  }

  @Override
  public void filter(ClientRequestContext request, ClientResponseContext response)
      throws IOException {
    String requestCorrelationId = extractCorrelationIdStringFromHeader(request.getHeaders());
    boolean responseCorrelationIdCopied = copyCorrelationIdToHeadersIfNotPresent(
        response.getHeaders().keySet()::contains,
        requestCorrelationId,
        response.getHeaders()::putSingle);

    logResponseToBeReceived(request, response, requestCorrelationId, responseCorrelationIdCopied);
  }

  private boolean addCorrelationIdIfNotPresent(
      Predicate<String> containsKey,
      BiConsumer<String, String> entrySetter) {
    if (!containsKey.test(CORRELATION_ID_HEADER_KEY)) {
      entrySetter.accept(CORRELATION_ID_HEADER_KEY, UUID.randomUUID().toString());
      return true;
    }
    return false;
  }

  private void logRequestToBeReceived(
      ContainerRequestContext request,
      boolean isCorrelationIdCreated) throws IOException {
    if (logger.isInfoEnabled()) {
      logger.logRequest(HttpTrafficLogObject.builder()
          .verb("Receiving")
          .requestMethod(request.getMethod())
          .adjective("on")
          .requestUri(request.getUriInfo().getAbsolutePath())
          .requestCorrelationId(request.getHeaders().get(CORRELATION_ID_HEADER_KEY).get(0))
          .requestCorrelationIdCreated(isCorrelationIdCreated)
          .headers(request.getHeaders())
          .requestCookies(request.getCookies().values())
          .entity(extractEntityFromStreamAndResetStream(
              request::getEntityStream,
              request::setEntityStream))
          .build());
    }
  }

  private Object extractEntityFromStreamAndResetStream(
      Supplier<InputStream> inputStreamGetter,
      Consumer<InputStream> inputStreamSetter) throws IOException {
    ByteArrayOutputStream entityOutputStream = toOutputStream(inputStreamGetter.get());
    inputStreamSetter.accept(new ByteArrayInputStream(entityOutputStream.toByteArray()));
    String entityString = entityOutputStream.toString(Charset.defaultCharset());
    if (!entityString.isBlank()) {
      return entityString;
    }
    return Collections.emptyList();
  }

  private ByteArrayOutputStream toOutputStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream replicaOutputStream = new ByteArrayOutputStream();
    while ((inputStream.available() > 0)) {
      replicaOutputStream.writeBytes(inputStream.readNBytes(StaticConfig.CHUNK_SIZE_TO_READ));
    }
    return replicaOutputStream;
  }

  private String extractCorrelationIdStringFromHeader(MultivaluedMap<String, ?> headers) {
    return Optional.ofNullable(headers.getFirst(CORRELATION_ID_HEADER_KEY))
        .map(Object::toString)
        .orElse("");
  }

  private boolean copyCorrelationIdToHeadersIfNotPresent(
      Predicate<String> containsKey,
      String existingCorrelationId,
      BiConsumer<String, ? super String> entrySetter) {
    if (!containsKey.test(CORRELATION_ID_HEADER_KEY) && Objects.nonNull(existingCorrelationId)) {
      entrySetter.accept(CORRELATION_ID_HEADER_KEY, existingCorrelationId);
      return true;
    }
    return false;
  }

  private void logResponseToBeSent(
      ContainerResponseContext response,
      boolean isResponseCorrelationIdCopied,
      ContainerRequestContext request,
      String requestCorrelationId) throws IOException {
    logger.logResponse(HttpTrafficLogObject.builder()
        .verb("Sending")
        .responseStatus(response.getStatus())
        .responseCorrelationId(response.getHeaders().getFirst(CORRELATION_ID_HEADER_KEY).toString())
        .responseCorrelationIdCopied(isResponseCorrelationIdCopied)
        .adjective("on")
        .requestMethod(request.getMethod())
        .requestUri(request.getUriInfo().getAbsolutePath())
        .requestCorrelationId(requestCorrelationId)
        .headers(convertToMapStringListOfString(response.getHeaders()))
        .responseCookies(response.getCookies().values())
        .entity(response.getEntity())
        .build());
  }

  private Map<String, List<String>> convertToMapStringListOfString(Map<String, List<Object>> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            entry -> entry.getValue().stream()
                .map(Object::toString)
                .collect(Collectors.toList())));
  }

  private void logRequestToBeSent(ClientRequestContext request, boolean correlationIdCreated)
      throws IOException {
    logger.logRequest(HttpTrafficLogObject.builder()
        .verb("Sending")
        .requestCorrelationIdCreated(correlationIdCreated)
        .requestMethod(request.getMethod())
        .adjective("to")
        .requestUri(request.getUri())
        .requestCorrelationId(Optional.of(request.getHeaders().getFirst(CORRELATION_ID_HEADER_KEY))
            .map(Object::toString)
            .orElse(""))
        .headers(convertToMapStringListOfString(request.getHeaders()))
        .requestCookies(request.getCookies().values())
        .entity(Optional.ofNullable(request.getEntity()).orElse(Collections.emptyList()))
        .build());
  }

  private void logResponseToBeReceived(
      ClientRequestContext request,
      ClientResponseContext response,
      String requestCorrelationId,
      boolean responseCorrelationIdCopied) throws IOException {
    logger.logResponse(HttpTrafficLogObject.builder()
        .verb("Receiving")
        .responseStatus(response.getStatus())
        .responseCorrelationId(response.getHeaders().getFirst(CORRELATION_ID_HEADER_KEY))
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
}
