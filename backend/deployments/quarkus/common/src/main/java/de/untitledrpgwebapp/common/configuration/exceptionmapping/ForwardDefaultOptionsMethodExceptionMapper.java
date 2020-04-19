package de.untitledrpgwebapp.common.configuration.exceptionmapping;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.spi.DefaultOptionsMethodException;

@Provider
public class ForwardDefaultOptionsMethodExceptionMapper
    implements ExceptionMapper<DefaultOptionsMethodException> {

  @Override
  public Response toResponse(DefaultOptionsMethodException exception) {
    return exception.getResponse();
  }
}
