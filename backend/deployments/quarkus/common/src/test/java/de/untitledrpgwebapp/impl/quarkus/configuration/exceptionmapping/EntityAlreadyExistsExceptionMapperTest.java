package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.domain.exception.EntityAlreadyExistsException;

class EntityAlreadyExistsExceptionMapperTest
    extends BaseExceptionMapperTest<EntityAlreadyExistsExceptionMapper,
    EntityAlreadyExistsException> {

  @Override
  EntityAlreadyExistsExceptionMapper getUut() {
    return new EntityAlreadyExistsExceptionMapper();
  }

  @Override
  EntityAlreadyExistsException getException() {
    EntityAlreadyExistsException exception =  mock(EntityAlreadyExistsException.class);
    when(exception.getMessage()).thenReturn(MESSAGE);
    when(exception.getCorrelationId()).thenReturn(CORRELATION_ID);
    return exception;
  }
}