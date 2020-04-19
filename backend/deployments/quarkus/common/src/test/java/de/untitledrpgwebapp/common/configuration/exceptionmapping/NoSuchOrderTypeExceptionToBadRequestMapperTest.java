package de.untitledrpgwebapp.common.configuration.exceptionmapping;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.common.exception.NoSuchOrderTypeException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for NoSuchAttributeExceptionToBadRequestMapper unit.")
class NoSuchOrderTypeExceptionToBadRequestMapperTest {

  @Test
  @DisplayName("Should return a response with expected values when toResponse is called.")
  void shouldReturnResponseWithExpectedValues() {
    // GIVEN
    NoSuchOrderTypeException exception = mock(NoSuchOrderTypeException.class);
    when(exception.getMessage()).thenReturn(BaseExceptionMapperTest.MESSAGE);

    // WHEN
    Response response = new NoSuchOrderTypeExceptionToBadRequestMapper().toResponse(exception);

    // THEN
    BaseExceptionMapperTest.assertThatResponseIsAsExpected(
        response,
        Status.BAD_REQUEST,
        BaseExceptionMapperTest.MESSAGE);
  }
}