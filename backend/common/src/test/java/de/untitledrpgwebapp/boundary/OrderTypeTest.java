package de.untitledrpgwebapp.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.untitledrpgwebapp.exception.NoSuchOrderTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for OrderType unit.")
class OrderTypeTest {

  @Test
  @DisplayName("Should return ASCENDING when called with null.")
  void shouldReturnAscendingWhenCalledWithNull() {
    // GIVEN: nothing

    // WHEN
    OrderType orderType = OrderType.fromString(null);

    // THEN
    assertThat(orderType, is(OrderType.ASCENDING));
  }
  @Test
  @DisplayName("Should convert \"ASC\" and \"ASCENDING\" to ASCENDING.")
  void shouldConvertToAscending() {
    // GIVEN: nothing

    // WHEN
    OrderType orderType = OrderType.fromString("ASC");

    // THEN
    assertThat(orderType, is(OrderType.ASCENDING));

    // WHEN
    orderType = OrderType.fromString("ascending");

    // THEN
    assertThat(orderType, is(OrderType.ASCENDING));
  }

  @Test
  @DisplayName("Should convert \"DESC\" and \"DESCENDING\" to DESCENDING.")
  void shouldConvertToDescending() {
    // GIVEN: nothing

    // WHEN
    OrderType orderType = OrderType.fromString("desc");

    // THEN
    assertThat(orderType, is(OrderType.DESCENDING));

    // WHEN
    orderType = OrderType.fromString("DESCENDING");

    // THEN
    assertThat(orderType, is(OrderType.DESCENDING));
  }

  @Test
  @DisplayName("Should throw NoSuchOrderTypeException when no fitting OrderType is found.")
  void shouldThrowNoSuchOrderTypeExceptionWhenNoFittingOrderTypeIsFound() {
    // GIVEN: nothing
    String noOrderType = "noOrderType";

    String expectedMessage = String.format(NoSuchOrderTypeException.MESSAGE_TEMPLATE, noOrderType);

    // WHEN
    NoSuchOrderTypeException exception = assertThrows(
        NoSuchOrderTypeException.class,
        () -> OrderType.fromString(noOrderType));

    // THEN
    assertThat(exception.getMessage(), is(expectedMessage));
  }

}