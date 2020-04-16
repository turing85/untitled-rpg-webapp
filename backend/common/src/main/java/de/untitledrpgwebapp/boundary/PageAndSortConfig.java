package de.untitledrpgwebapp.boundary;

/**
 * This interface defines the general structure of pagination for a request.
 */
public interface PageAndSortConfig {

  /**
   * Return the page (a {@code long >= 0}).
   */
  int getPage();

  /**
   * Returns the size  (Q {@code int >= 0}).
   */
  int getSize();

  /**
   * Returns the attribute to sort by (Optional, may return {@code null}).
   *
   * @return the attribute to sort by.
   */
  String getSortBy();

  /**
   * Returns the explicit order for the attribute to sort by. This must be on of (ignoring cases):
   * <ul>
   *   <li>{@code "ASC"}</li>
   *   <li>{@code "ASCENDING"}</li>
   *   <li>{@code "DESC"}</li>
   *   <li>{@code "DESCENDING"}</li>
   * </ul>
   *
   * @return the requested order
   */
  String getOrder();
}
