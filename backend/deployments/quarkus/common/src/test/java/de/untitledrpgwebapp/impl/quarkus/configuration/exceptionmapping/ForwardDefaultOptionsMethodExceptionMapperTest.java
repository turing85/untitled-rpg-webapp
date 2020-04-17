package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import javax.ws.rs.core.Response;
import org.jboss.resteasy.spi.DefaultOptionsMethodException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ForwardDefaultOptionsMethodExceptionMapper unit.")
class ForwardDefaultOptionsMethodExceptionMapperTest {

  @Test
  @DisplayName("Should return the Response form the exception when toResponse is called")
  void shouldReturnResponseOfExceptionWhenToResponseIsCalled() {
    // GIVEN
    Response response = Response.ok().build();
    DefaultOptionsMethodException exception = new DefaultOptionsMethodException("Oops", response);

    // WHEN
    Response actual = new ForwardDefaultOptionsMethodExceptionMapper().toResponse(exception);

    // THEN
    assertThat(actual, sameInstance(response));
  }

}