package de.untitledrpgwebapp.common.configuration.exceptionmapping;

import de.untitledrpgwebapp.common.boundary.exception.NoSuchAttributeException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchAttributeExceptionToBadRequestMapper
    implements ExceptionMapper<NoSuchAttributeException> {

  @Override
  public Response toResponse(NoSuchAttributeException exception) {
    return Response.status(Status.BAD_REQUEST.getStatusCode())
        .entity(ErrorResponseEntity.builder().message(exception.getMessage()).build())
        .build();
  }
}
