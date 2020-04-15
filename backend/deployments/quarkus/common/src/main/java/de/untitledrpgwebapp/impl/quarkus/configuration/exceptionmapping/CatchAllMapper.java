package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CatchAllMapper implements ExceptionMapper<RuntimeException> {

  public static final String MESSAGE_TEMPLATE =
      "Caught unexpected exception, returning a 500 INTERNAL SERVER ERROR. Exception: ";
  public static final String RESPONSE_MESSAGE = "uh oh! It seems the Deckers got into the system!";
  private final Logger logger;

  public CatchAllMapper() {
    this(LoggerFactory.getLogger(CatchAllMapper.class));
  }

  @Override
  public Response toResponse(RuntimeException exception) {
    logger.error(
        MESSAGE_TEMPLATE,
        exception);
    return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
        .entity(ErrorResponseEntity.builder()
            .message(RESPONSE_MESSAGE)
            .build())
        .build();
  }
}
