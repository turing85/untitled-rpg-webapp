package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import de.untitledrpgwebapp.domain.exception.DependencyNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class DependencyNotFoundExceptionMapper
    implements BaseExceptionMapper<DependencyNotFoundException> {

  public static final Status STATUS_TO_MAP_TO = Status.FORBIDDEN;

  @Override
  public Response toResponse(DependencyNotFoundException exception) {
    return toResponse(exception, STATUS_TO_MAP_TO);
  }

  @Override
  public Status statusToMapTo() {
    return STATUS_TO_MAP_TO;
  }
}
