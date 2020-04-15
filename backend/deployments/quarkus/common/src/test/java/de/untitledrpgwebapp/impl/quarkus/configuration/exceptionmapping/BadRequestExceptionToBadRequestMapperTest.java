package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.domain.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Tests for BadRequestExceptionToBadRequestMapper unit.")
class BadRequestExceptionToBadRequestMapperTest extends
    BaseExceptionMapperTest<BadRequestExceptionToBadRequestMapper, BadRequestException> {

  @Override
  BadRequestExceptionToBadRequestMapper getUut() {
    return new BadRequestExceptionToBadRequestMapper();
  }

  @Override
  BadRequestException getException() {
    BadRequestException exception = mock(BadRequestException.class);
    when(exception.getMessage()).thenReturn(MESSAGE);
    when(exception.getCorrelationId()).thenReturn(CORRELATION_ID);
    return exception;
  }
}