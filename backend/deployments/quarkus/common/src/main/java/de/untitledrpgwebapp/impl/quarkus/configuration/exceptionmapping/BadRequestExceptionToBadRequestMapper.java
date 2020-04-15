package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import de.untitledrpgwebapp.domain.exception.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionToBadRequestMapper implements
    BaseExceptionMapper<BadRequestException> {

  public static final Status STATUS_TO_MAP_TO = Status.BAD_REQUEST;

  @Override
  public Response toResponse(BadRequestException exception) {
    return toResponse(exception, STATUS_TO_MAP_TO);
  }

  @Override
  public Status statusToMapTo() {
    return STATUS_TO_MAP_TO;
  }
}
