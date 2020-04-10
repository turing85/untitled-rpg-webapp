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
public class LanguageEnglish {

  private final Logger logger;

  public void onApplicationStartup(@Observes StartupEvent startupEvent) {
    logger.info("Setting language to english");
    System.setProperty("user.language", "en");
  }
}
