package de.untitledrpgwebapp.common.configuration.logging;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Integer.MAX_VALUE)
public class LogContainerResponseFilter extends LogResponseFilter
    implements ContainerResponseFilter {

  public LogContainerResponseFilter() {
    this(new HttpTrafficLogger());
  }

  protected LogContainerResponseFilter(HttpTrafficLogger logger) {
    super(logger);
  }

  @Override
  public void filter(ContainerRequestContext request, ContainerResponseContext response)
      throws IOException {
    String requestCorrelationId = extractCorrelationIdStringFromHeader(request.getHeaders());
    MultivaluedMap<String, Object> responseHeaders = response.getHeaders();
    boolean isResponseCorrelationIdCopied = copyCorrelationIdToHeadersIfNotPresent(
        responseHeaders.keySet()::contains,
        requestCorrelationId,
        responseHeaders::putSingle);

    logResponseToBeSent(response, isResponseCorrelationIdCopied, request, requestCorrelationId);
  }

  private void logResponseToBeSent(
      ContainerResponseContext response,
      boolean isResponseCorrelationIdCopied,
      ContainerRequestContext request,
      String requestCorrelationId) throws IOException {
    if (getLogger().isInfoEnabled()) {
      getLogger().logResponse(HttpTrafficLogObject.builder()
          .verb("Sending")
          .responseStatus(response.getStatus())
          .responseCorrelationId(response.getHeaders()
              .getFirst(StaticConfig.CORRELATION_ID_HEADER_KEY)
              .toString())
          .responseCorrelationIdCopied(isResponseCorrelationIdCopied)
          .adjective("on")
          .requestMethod(request.getMethod())
          .requestUri(request.getUriInfo().getRequestUri())
          .requestCorrelationId(requestCorrelationId)
          .headers(convertToMapStringListOfString(response.getHeaders()))
          .responseCookies(response.getCookies().values())
          .entity(response.getEntity())
          .build());
    }
  }
}
