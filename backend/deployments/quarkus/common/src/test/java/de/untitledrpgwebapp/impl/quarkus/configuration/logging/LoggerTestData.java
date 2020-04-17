package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import static org.mockito.Mockito.mock;

import java.net.URI;
import java.util.HashMap;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.UriInfo;

class LoggerTestData {

  public static final String ENTITY = "entity";
  public static final int RESPONSE_STATUS = 200;
  public static final String RESPONSE_CORRELATION_ID = "responseCorrelationId";
  public static final String REQUEST_METHOD = "requestMethod";
  public static final URI REQUEST_URI = URI.create("http://localhost");
  public static final String REQUEST_CORRELATION_ID = "requestCorrelationId";

  protected final HttpTrafficLogger logger = mock(HttpTrafficLogger.class);
  protected final ContainerResponseContext containerResponse = mock(ContainerResponseContext.class);
  protected final ContainerRequestContext containerRequest = mock(ContainerRequestContext.class);
  protected final ClientRequestContext clientRequest = mock(ClientRequestContext.class);
  protected final ClientResponseContext clientResponse = mock(ClientResponseContext.class);
  protected final UriInfo requestUriInfo = mock(UriInfo.class);
  protected final MultivaluedHashMap<String, String> containerRequestHeadersWithoutCorrelationId =
      new MultivaluedHashMap<>();
  protected final MultivaluedHashMap<String, Object> containerResponseHeadersWithoutCorrelationId =
      new MultivaluedHashMap<>();
  protected final MultivaluedHashMap<String, Object> clientRequestHeadersWithoutCorrelationId =
      new MultivaluedHashMap<>();
  protected final MultivaluedHashMap<String, String> clientResponseHeadersWithoutCorrelationId =
      new MultivaluedHashMap<>();
  protected final HashMap<String, Cookie> requestCookies = new HashMap<>();
  protected final HashMap<String, NewCookie> responseCookies = new HashMap<>();

  protected MultivaluedHashMap<String, String> containerRequestHeadersWithCorrelationId;
  protected MultivaluedHashMap<String, Object> containerResponseHeadersWithCorrelationId;
  protected MultivaluedHashMap<String, Object> clientRequestHeadersWithCorrelationId;
  protected MultivaluedHashMap<String, String> clientResponseHeadersWithCorrelationId;
}