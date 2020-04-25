package de.untitledrpgwebapp.common.configuration.logging;

import java.io.IOException;
import java.util.Optional;
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
    Optional<String> correlationId = addCorrelationIdToThreadLocalContext(request.getHeaders());
    if (getLogger().isInfoEnabled()) {
      getLogger().logRequest(HttpTrafficLogObject.builder()
          .verb("Receiving")
          .requestMethod(request.getMethod())
          .adjective("on")
          .requestUri(request.getUriInfo().getRequestUri())
          .requestCorrelationId(correlationId.orElse(""))
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
