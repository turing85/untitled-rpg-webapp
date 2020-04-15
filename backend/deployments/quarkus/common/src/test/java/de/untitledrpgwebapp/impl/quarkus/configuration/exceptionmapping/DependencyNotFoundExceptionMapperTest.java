package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.domain.exception.DependencyNotFoundException;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Tests for DependencyNotFoundExceptionMapper unit.")
class DependencyNotFoundExceptionMapperTest
    extends BaseExceptionMapperTest<DependencyNotFoundExceptionMapper, DependencyNotFoundException> {

  @Override
  DependencyNotFoundExceptionMapper getUut() {
    return new DependencyNotFoundExceptionMapper();
  }

  @Override
  DependencyNotFoundException getException() {
    DependencyNotFoundException exception = mock(DependencyNotFoundException.class);
    when(exception.getMessage()).thenReturn(MESSAGE);
    when(exception.getCorrelationId()).thenReturn(CORRELATION_ID);
    return exception;
  }
}