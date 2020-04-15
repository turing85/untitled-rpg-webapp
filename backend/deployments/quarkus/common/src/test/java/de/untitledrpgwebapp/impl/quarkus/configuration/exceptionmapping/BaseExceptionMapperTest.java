package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import de.untitledrpgwebapp.domain.exception.BusinessException;
import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public abstract class BaseExceptionMapperTest<
    M extends BaseExceptionMapper<E>,
    E extends BusinessException> {
  public static final String MESSAGE = "message";
  public static final UUID CORRELATION_ID = UUID.randomUUID();

  @Test
  @DisplayName("Should return a response with expected values when toResponse is called.")
  void shouldReturnResponseWithExpectedValues() {
    // GIVEN
    E exception = getException();

    // WHEN
    Response response = getUut().toResponse(exception);

    // THEN
    assertThatResponseIsAsExpected(response, getUut().statusToMapTo());
    assertThatCorrelationIdHeaderIsSetToExpectedValue(response);
  }

  abstract M getUut();

  abstract E getException();

  static void assertThatResponseIsAsExpected(Response response, Status status) {
    assertThatResponseIsAsExpected(response, status, MESSAGE);
  }

  static void assertThatResponseIsAsExpected(Response response, Status status, String message) {
    assertThat(response, is(notNullValue()));
    assertThat(response.getStatus(), is(status.getStatusCode()));

    assertThat(response.getEntity(), instanceOf(ErrorResponseEntity.class));
    ErrorResponseEntity entity = (ErrorResponseEntity) response.getEntity();
    assertThat(entity.getMessage(), is(message));
  }

  static void assertThatCorrelationIdHeaderIsSetToExpectedValue(Response response) {
    List<Object> correlationIdHeaders =
        response.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY);
    assertThat(correlationIdHeaders, hasSize(1));
    assertThat(correlationIdHeaders.get(0), is(CORRELATION_ID));
  }
}
