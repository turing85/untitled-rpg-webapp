package de.untitledrpgwebapp.common.configuration.logging;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
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
    if (!containsKey.test(StaticConfig.CORRELATION_ID_HEADER_KEY)) {
      entrySetter.accept(StaticConfig.CORRELATION_ID_HEADER_KEY, UUID.randomUUID().toString());
      return true;
    }
    return false;
  }
}
