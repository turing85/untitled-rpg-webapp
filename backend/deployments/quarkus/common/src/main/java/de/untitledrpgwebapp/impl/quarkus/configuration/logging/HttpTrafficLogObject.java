package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class HttpTrafficLogObject {

  private String verb;
  private int responseStatus;
  private String responseCorrelationId;
  private String requestMethod;
  private String adjective;
  private URI requestUri;
  private String requestCorrelationId;
  private boolean responseCorrelationIdCopied;
  private boolean requestCorrelationIdAdded;
  private Map<String, List<String>> headers;
  private Collection<NewCookie> responseCookies;
  private Collection<Cookie> requestCookies;
  private Object entity;
}
