package de.untitledrpgwebapp.common.configuration.exceptionmapping;

import de.untitledrpgwebapp.common.domain.exception.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionToNotFoundMapper
    implements BaseExceptionMapper<EntityNotFoundException> {

  @Override
  public Response toResponse(EntityNotFoundException exception) {
    return toResponse(exception, statusToMapTo());
  }

  @Override
  public Status statusToMapTo() {
    return Status.NOT_FOUND;
  }
}
