package de.untitledrpgwebapp.impl.quarkus.configuration;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;

@ApplicationScoped
@Startup
@AllArgsConstructor
public class TimeZoneUtc {

  private final Logger logger;

  public void onApplicationStartup(@Observes StartupEvent startupEvent) {
    logger.info("Setting timezone to UTC");
    System.setProperty("user.timezone", "UTC");
  }
}
