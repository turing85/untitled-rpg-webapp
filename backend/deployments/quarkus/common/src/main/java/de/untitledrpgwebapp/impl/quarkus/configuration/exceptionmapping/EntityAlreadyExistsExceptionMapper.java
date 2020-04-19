package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import de.untitledrpgwebapp.domain.exception.EntityAlreadyExistsException;
import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityAlreadyExistsExceptionMapper
    implements BaseExceptionMapper<EntityAlreadyExistsException> {
  @Override
  public Status statusToMapTo() {
    return Status.CONFLICT;
  }

  @Override
  public Response toResponse(EntityAlreadyExistsException exception) {
    return Response.status(statusToMapTo().getStatusCode())
        .entity(ErrorResponseEntity.builder().message(exception.getMessage()).build())
        .header(StaticConfig.CORRELATION_ID_HEADER_KEY, exception.getCorrelationId())
        .build();
  }
}
