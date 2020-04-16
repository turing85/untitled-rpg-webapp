package de.untitledrpgwebapp.boundary;

import java.util.Objects;
import java.util.Optional;

/**
 * An enum representing possible sort orders.
 */
public enum SortType {
  ASCENDING, DESCENDING;

  /**
   * Static method to determine the SortOrder from a {@link String} representation. May return empty
   * Optional if no matching Type is found
   *
   * @param string
   *     the Type to search for
   *
   * @return the type (if any) or an empty {@link Optional}.
   */
  public static Optional<SortType> fromString(String string) {
    if (Objects.nonNull(string)) {
      if ("ASCENDING".equalsIgnoreCase(string) || "ASC".equalsIgnoreCase(string)) {
        return Optional.of(ASCENDING);
      } else if ("DESCENDING".equalsIgnoreCase(string) || "DESC".equalsIgnoreCase(string)) {
        return Optional.of(DESCENDING);
      }
    }
    return Optional.empty();
  }
}