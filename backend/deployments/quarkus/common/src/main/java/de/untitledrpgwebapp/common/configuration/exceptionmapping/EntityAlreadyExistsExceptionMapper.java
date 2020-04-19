package de.untitledrpgwebapp.common.configuration.exceptionmapping;

import de.untitledrpgwebapp.common.domain.exception.EntityAlreadyExistsException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityAlreadyExistsExceptionMapper
    implements BaseExceptionMapper<EntityAlreadyExistsException> {

  @Override
  public Response toResponse(EntityAlreadyExistsException exception) {
    return toResponse(exception, statusToMapTo());
  }

  @Override
  public Status statusToMapTo() {
    return Status.CONFLICT;
  }
}
