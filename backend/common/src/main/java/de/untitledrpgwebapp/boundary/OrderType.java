package de.untitledrpgwebapp.boundary;

import de.untitledrpgwebapp.exception.NoSuchOrderTypeException;
import java.util.Objects;

/**
 * An enum representing possible sort orders.
 */
public enum OrderType {
  ASCENDING, DESCENDING;

  /**
   * Static method to determine the SortOrder from a {@link String} representation. May return empty
   * Optional if no matching Type is found
   *
   * @param orderType
   *     the Type to search for
   *
   * @return the type or {@link #ASCENDING} if {@code oderType} is {@code null}.
   */
  public static OrderType fromString(String orderType) {
    if (Objects.isNull(orderType)) {
      return ASCENDING;
    }
    if ("ASCENDING".equalsIgnoreCase(orderType) || "ASC".equalsIgnoreCase(orderType)) {
      return ASCENDING;
    } else if ("DESCENDING".equalsIgnoreCase(orderType) || "DESC".equalsIgnoreCase(orderType)) {
      return DESCENDING;
    } else {
      throw new NoSuchOrderTypeException(orderType);
    }

  }
}