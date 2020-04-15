package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

@DisplayName("Tests for CatchAllMapper unit.")
class CatchAllMapperTest {

  @Test
  @DisplayName("Should log the caught exception as error and return the expected response.")
  void shouldLogExceptionAndMapExceptionToInternalServerErrorWhenToResponseIsCalled() {
    // GIVEN
    RuntimeException exception = mock(RuntimeException.class);
    when(exception.getMessage()).thenReturn(BaseExceptionMapperTest.MESSAGE);
    Logger logger = mock(Logger.class);

    // WHEN
    Response response = new CatchAllMapper(logger).toResponse(exception);

    // THEN
    BaseExceptionMapperTest.assertThatResponseIsAsExpected(
        response,
        Status.INTERNAL_SERVER_ERROR,
        CatchAllMapper.RESPONSE_MESSAGE);

    verify(logger).error(CatchAllMapper.MESSAGE_TEMPLATE, exception);
  }
}