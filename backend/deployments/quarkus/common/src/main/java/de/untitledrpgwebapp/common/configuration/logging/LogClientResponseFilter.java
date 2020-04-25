package de.untitledrpgwebapp.common.configuration.logging;

import java.io.IOException;
import java.util.Optional;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class LogClientResponseFilter extends LogResponseFilter
    implements ClientResponseFilter {

  public LogClientResponseFilter() {
    this(new HttpTrafficLogger());
  }

  protected LogClientResponseFilter(HttpTrafficLogger logger) {
    super(logger);
  }

  @Override
  public void filter(ClientRequestContext request, ClientResponseContext response)
      throws IOException {
    String requestCorrelationId = extractCorrelationIdStringFromHeader(request.getHeaders());
    boolean responseCorrelationIdCopied = copyCorrelationIdToHeadersIfNotPresent(
        response.getHeaders().keySet()::contains,
        requestCorrelationId,
        response.getHeaders()::putSingle);

    logResponseToBeReceived(request, response, requestCorrelationId, responseCorrelationIdCopied);
  }

  private void logResponseToBeReceived(
      ClientRequestContext request,
      ClientResponseContext response,
      String requestCorrelationId,
      boolean responseCorrelationIdCopied) throws IOException {
    Optional<String> correlationId = addCorrelationIdToThreadLocalContext(response.getHeaders());
    if (getLogger().isInfoEnabled()) {
      getLogger().logResponse(HttpTrafficLogObject.builder()
          .verb("Receiving")
          .responseStatus(response.getStatus())
          .responseCorrelationId(correlationId.orElse(""))
          .responseCorrelationIdCopied(responseCorrelationIdCopied)
          .requestMethod(request.getMethod())
          .adjective("to")
          .requestUri(request.getUri())
          .requestCorrelationId(requestCorrelationId)
          .headers(response.getHeaders())
          .responseCookies(response.getCookies().values())
          .entity(extractEntityFromStreamAndResetStream(
              response::getEntityStream,
              response::setEntityStream))
          .build());
    }
  }
}
