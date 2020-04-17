package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import static de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig.CORRELATION_ID_HEADER_KEY;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class LogContainerRequestFilter extends LogRequestFilter
    implements ContainerRequestFilter {

  public LogContainerRequestFilter() {
    this(new HttpTrafficLogger());
  }

  protected LogContainerRequestFilter(HttpTrafficLogger logger) {
    super(logger);
  }

  @Override
  public void filter(ContainerRequestContext request) throws IOException {
    boolean correlationIdAdded = addCorrelationIdIfNotPresent(
        request.getHeaders().keySet()::contains,
        request.getHeaders()::putSingle);

    logRequestToBeReceived(request, correlationIdAdded);
  }

  protected void logRequestToBeReceived(
      ContainerRequestContext request,
      boolean isCorrelationIdCreated) throws IOException {
    if (getLogger().isInfoEnabled()) {
      getLogger().logRequest(HttpTrafficLogObject.builder()
          .verb("Receiving")
          .requestMethod(request.getMethod())
          .adjective("on")
          .requestUri(request.getUriInfo().getRequestUri())
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
}
