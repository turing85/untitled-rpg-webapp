package de.untitledrpgwebapp.imp.quarkus.configuration.exceptionmapping;

import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionToBadRequestMapper implements
    ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    StringBuilder message = new StringBuilder();
    for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
      message.append(constructViolationDescription(
          getPropertyNameFromPath(violation).orElse("(unnamed)"),
          violation.getInvalidValue(),
          violation.getMessage()));
    }
    return Response.status(Status.BAD_REQUEST).entity(message.toString()).build();
  }

  private String constructViolationDescription(
      String propertyName,
      Object invalidValue,
      String message) {
    return String.format("Parameter %s (=\"%s\"): %s%n", propertyName, invalidValue, message);
  }

  private Optional<String> getPropertyNameFromPath(ConstraintViolation<?> violation) {
    String propertyName = null;
    for (Path.Node node : violation.getPropertyPath()) {
      propertyName = node.getName();
    }
    return Optional.ofNullable(propertyName);
  }
}
