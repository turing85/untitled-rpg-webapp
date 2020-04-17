package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import static de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig.CORRELATION_ID_HEADER_KEY;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class LogRequestFilter extends LogFilter {

  protected LogRequestFilter(HttpTrafficLogger logger) {
    super(logger);
  }

  protected boolean addCorrelationIdIfNotPresent(
      Predicate<String> containsKey,
      BiConsumer<String, String> entrySetter) {
    if (!containsKey.test(CORRELATION_ID_HEADER_KEY)) {
      entrySetter.accept(CORRELATION_ID_HEADER_KEY, UUID.randomUUID().toString());
      return true;
    }
    return false;
  }
}
