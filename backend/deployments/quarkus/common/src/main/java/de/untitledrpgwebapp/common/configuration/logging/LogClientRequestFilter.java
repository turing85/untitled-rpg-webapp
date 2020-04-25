package de.untitledrpgwebapp.common.configuration.logging;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class LogClientRequestFilter extends LogRequestFilter
    implements ClientRequestFilter {

  public LogClientRequestFilter() {
    this(new HttpTrafficLogger());
  }

  protected LogClientRequestFilter(HttpTrafficLogger logger) {
    super(logger);
  }

  @Override
  public void filter(ClientRequestContext request) throws IOException {
    boolean correlationIdCreated = addCorrelationIdIfNotPresent(
        request.getHeaders().keySet()::contains,
        request.getHeaders()::putSingle);

    logRequestToBeSent(request, correlationIdCreated);
  }

  private void logRequestToBeSent(ClientRequestContext request, boolean correlationIdCreated)
      throws IOException {
    if (getLogger().isInfoEnabled()) {
      getLogger().logRequest(HttpTrafficLogObject.builder()
          .verb("Sending")
          .requestCorrelationIdCreated(correlationIdCreated)
          .requestMethod(request.getMethod())
          .adjective("to")
          .requestUri(request.getUri())
          .requestCorrelationId(Optional.of(request.getHeaders()
              .getFirst(StaticConfig.CORRELATION_ID_HEADER_KEY))
              .map(Object::toString)
              .orElse(""))
          .headers(convertToMapStringListOfString(request.getHeaders()))
          .requestCookies(request.getCookies().values())
          .entity(Optional.ofNullable(request.getEntity()).orElse(Collections.emptyList()))
          .build());
    }
  }
}
