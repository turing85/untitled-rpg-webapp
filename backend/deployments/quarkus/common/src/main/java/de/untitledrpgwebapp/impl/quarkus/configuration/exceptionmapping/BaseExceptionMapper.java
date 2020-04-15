package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import de.untitledrpgwebapp.domain.exception.BusinessException;
import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public interface BaseExceptionMapper<E extends BusinessException> extends ExceptionMapper<E> {

  /**
   * Default helper method that converts some {@link BusinessException} into a uniform {@link
   * Response}.
   *
   * @param exception
   *     the exception to map.
   * @param status
   *     the status for the Response.
   *
   * @return The response.
   *
   * @see ErrorResponseEntity
   */
  default Response toResponse(E exception, Status status) {
    return Response.status(status.getStatusCode())
        .entity(ErrorResponseEntity.builder().message(exception.getMessage()).build())
        .header(StaticConfig.CORRELATION_ID_HEADER_KEY, exception.getCorrelationId())
        .build();
  }

  Status statusToMapTo();
}
