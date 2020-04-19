package de.untitledrpgwebapp.common.configuration.exceptionmapping;

import de.untitledrpgwebapp.common.domain.exception.DependencyNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class DependencyNotFoundExceptionMapper
    implements BaseExceptionMapper<DependencyNotFoundException> {

  @Override
  public Response toResponse(DependencyNotFoundException exception) {
    return toResponse(exception, statusToMapTo());
  }

  @Override
  public Status statusToMapTo() {
    return Status.CONFLICT;
  }
}
