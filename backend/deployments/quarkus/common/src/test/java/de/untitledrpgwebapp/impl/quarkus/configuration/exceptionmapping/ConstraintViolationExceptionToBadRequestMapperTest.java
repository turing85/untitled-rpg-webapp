package de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping;

import static de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping.ConstraintViolationExceptionToBadRequestMapper.BODY_FORMAT;
import static de.untitledrpgwebapp.impl.quarkus.configuration.exceptionmapping.ConstraintViolationExceptionToBadRequestMapper.UNNAMED_PROPERTY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ConstraintViolationExceptionToBadRequestMapper unit.")
class ConstraintViolationExceptionToBadRequestMapperTest {

  String someNode = "someNode";
  String propertyNode = "propertyNode";
  String invalidValue = "invalidValue";
  Path.Node firstNode = mock(Path.Node.class);
  Path.Node secondNode = mock(Path.Node.class);
  List<Path.Node> nodes = List.of(firstNode, secondNode);

  Path path = mock(Path.class);

  ConstraintViolation<?> violation = mock(ConstraintViolation.class);

  ConstraintViolationException exception = mock(ConstraintViolationException.class);

  @BeforeEach
  void setup() {
    when(firstNode.getName()).thenReturn(someNode);
    when(secondNode.getName()).thenReturn(propertyNode);

    when(path.iterator()).thenReturn(nodes.iterator());

    when(violation.getPropertyPath()).thenReturn(path);
    when(violation.getInvalidValue()).thenReturn(invalidValue);
    when(violation.getMessage()).thenReturn(BaseExceptionMapperTest.MESSAGE);

    when(exception.getConstraintViolations()).thenReturn(Set.of(violation));
  }

  @Test
  @DisplayName("Should return a 404 (NOT FOUND) response with expected values when toResponse is "
      + "called.")
  void shouldMapConstraintViolationExceptionToBadRequestResponseWhenToResponseIsCalled() {
    // GIVEN
    String expectedEntity =
        String.format(BODY_FORMAT, propertyNode, invalidValue, BaseExceptionMapperTest.MESSAGE);

    // WHEN
    Response actual = new ConstraintViolationExceptionToBadRequestMapper().toResponse(exception);

    // THEN
    BaseExceptionMapperTest
        .assertThatResponseIsAsExpected(actual, Status.BAD_REQUEST, expectedEntity);
  }

  @Test
  @DisplayName("Should replace null property in Violation with UNNAMED_PROPERTY in response when "
      + "toResponse is called.")
  void shouldReplaceNullPropertyWithUnnamedPropertyInResponseWhenToResponseIsCalled() {
    // GIVEN
    when(path.iterator()).thenReturn(Collections.emptyIterator());

    String message =
        String.format(BODY_FORMAT, UNNAMED_PROPERTY, invalidValue, BaseExceptionMapperTest.MESSAGE);

    // WHEN
    Response actual = new ConstraintViolationExceptionToBadRequestMapper().toResponse(exception);

    // THEN
    BaseExceptionMapperTest.assertThatResponseIsAsExpected(actual, Status.BAD_REQUEST, message);
  }
}