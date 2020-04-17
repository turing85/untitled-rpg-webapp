package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.spi.DefaultOptionsMethodException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ForwardWebApplicationExceptionMapper unit.")
class ForwardWebApplicationExceptionMapperTest {

  @Test
  @DisplayName("Should return the Response form the exception when toResponse is called")
  void shouldReturnResponseOfExceptionWhenToResponseIsCalled() {
    // GIVEN
    Response response = Response.ok().build();
    WebApplicationException exception = new WebApplicationException("Oops", response);

    // WHEN
    Response actual = new ForwardWebApplicationExceptionMapper().toResponse(exception);

    // THEN
    assertThat(actual, sameInstance(response));
  }
}