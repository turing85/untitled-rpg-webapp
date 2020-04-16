package de.untitledrpgwebapp.impl.quarkus.configuration;

import java.util.List;

public final class StaticConfig {

  public static final int CHUNK_SIZE_TO_READ = 1024;
  public static final String CORRELATION_ID_HEADER_KEY = "X-Correlation-ID";
  public static final String CORRELATION_ID_LOG_NAME = "correlationId";
  public static final List<String> COOKIES_TO_OBFUSCATE =
      List.of("access_token", "refresh_token", "id_token");
  public static final List<String> HEADERS_TO_OBFUSCATE = List.of("Authorization");
  public static final List<String> BODY_ATTRIBUTES_TO_OBFUSCATE =
      List.of("password", "(?:[^\"]*_token)", "credentials");
  public static final String OBFUSCATION_VALUE = "<redacted>";
  public static final String DEFAULT_PAGINATION_LIMIT = "20";
  public static final String DEFAULT_PAGINATION_ORDER = "ASC";

  private StaticConfig() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
