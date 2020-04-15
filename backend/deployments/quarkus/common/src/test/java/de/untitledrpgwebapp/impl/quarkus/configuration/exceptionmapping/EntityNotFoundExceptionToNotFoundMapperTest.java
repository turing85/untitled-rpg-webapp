package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.domain.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Tests for EntityNotFoundExceptionToNotFoundMapper unit.")
class EntityNotFoundExceptionToNotFoundMapperTest
    extends BaseExceptionMapperTest<EntityNotFoundExceptionToNotFoundMapper,
    EntityNotFoundException> {

  @Override
  EntityNotFoundExceptionToNotFoundMapper getUut() {
    return new EntityNotFoundExceptionToNotFoundMapper();
  }

  @Override
  EntityNotFoundException getException() {
    EntityNotFoundException exception = mock(EntityNotFoundException.class);
    when(exception.getMessage()).thenReturn(MESSAGE);
    when(exception.getCorrelationId()).thenReturn(CORRELATION_ID);
    return exception;
  }
}