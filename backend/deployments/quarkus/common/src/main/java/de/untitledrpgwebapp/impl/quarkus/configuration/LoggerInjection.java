package de.untitledrpgwebapp.impl.quarkus.configuration;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class LoggerInjection {

  /**
   * Producer enabling logger injection.
   *
   * @param injectionPoint
   *     injectionPoint.
   *
   * @return the logger instance to inject.
   */
  @Produces
  public Logger logger(InjectionPoint injectionPoint) {
    return LoggerFactory.getLogger(Optional.ofNullable(
        injectionPoint.getMember().getDeclaringClass().getName())
        .orElseThrow(IllegalArgumentException::new));
  }
}
