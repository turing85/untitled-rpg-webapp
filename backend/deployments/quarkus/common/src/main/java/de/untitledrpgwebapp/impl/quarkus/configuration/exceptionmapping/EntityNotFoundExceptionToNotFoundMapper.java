package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import de.untitledrpgwebapp.domain.exception.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionToNotFoundMapper
    implements BaseExceptionMapper<EntityNotFoundException> {

  public static final Status STATUS_TO_MAP_TO = Status.NOT_FOUND;

  @Override
  public Response toResponse(EntityNotFoundException exception) {
    return toResponse(exception, STATUS_TO_MAP_TO);
  }

  @Override
  public Status statusToMapTo() {
    return STATUS_TO_MAP_TO;
  }
}
