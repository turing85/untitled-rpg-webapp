package de.untitledrpgwebapp.impl.quarkus.configuration;

import java.util.List;

public final class StaticConfig {

  public static final int CHUNK_SIZE_TO_READ = 1024;
  public static final String EMPTY_BODY = "(empty)";
  public static final String X_CORRELATION_ID_HEADER = "X-Correlation-ID";
  public static final List<String> cookiesToAnonymize =
      List.of("access_token", "refresh_token", "id_token");
  public static final List<String> headersToAnonymize = List.of("Authorization");
  public static final String OBFUSCATION_VALUE = "****";

  private StaticConfig() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
