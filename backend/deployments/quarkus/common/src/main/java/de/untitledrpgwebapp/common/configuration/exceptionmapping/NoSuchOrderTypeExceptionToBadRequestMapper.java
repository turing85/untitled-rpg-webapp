package de.untitledrpgwebapp.common.configuration.exceptionmapping;

import de.untitledrpgwebapp.common.exception.NoSuchOrderTypeException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchOrderTypeExceptionToBadRequestMapper
    implements ExceptionMapper<NoSuchOrderTypeException> {

  @Override
  public Response toResponse(NoSuchOrderTypeException exception) {
    return Response.status(Status.BAD_REQUEST.getStatusCode())
        .entity(ErrorResponseEntity.builder().message(exception.getMessage()).build())
        .build();
  }
}
