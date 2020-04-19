package de.untitledrpgwebapp.common.boundary;

/**
 * This interface defines the general structure of pagination for a request.
 */
public interface PageAndSortConfig {

  /**
   * Return the offset (a {@code long >= 0}).
   */
  int getOffset();

  /**
   * Returns the limit  (Q {@code int >= 1}).
   */
  int getLimit();

  /**
   * Returns the attribute to order by (Optional, may return {@code null}).
   *
   * @return the attribute to order by.
   */
  String getOrderBy();

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
