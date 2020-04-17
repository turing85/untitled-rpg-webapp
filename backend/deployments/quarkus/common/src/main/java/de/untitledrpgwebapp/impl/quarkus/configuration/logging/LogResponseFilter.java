package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import static de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig.CORRELATION_ID_HEADER_KEY;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import javax.ws.rs.core.MultivaluedMap;

public abstract class LogResponseFilter extends LogFilter {

  protected LogResponseFilter(HttpTrafficLogger logger) {
    super(logger);
  }

  protected String extractCorrelationIdStringFromHeader(MultivaluedMap<String, ?> headers) {
    return Optional.ofNullable(headers.getFirst(CORRELATION_ID_HEADER_KEY))
        .map(Object::toString)
        .orElse("");
  }

  protected boolean copyCorrelationIdToHeadersIfNotPresent(
      Predicate<String> containsKey,
      String existingCorrelationId,
      BiConsumer<String, ? super String> entrySetter) {
    if (!containsKey.test(CORRELATION_ID_HEADER_KEY) && Objects.nonNull(existingCorrelationId)) {
      entrySetter.accept(CORRELATION_ID_HEADER_KEY, existingCorrelationId);
      return true;
    }
    return false;
  }
}
