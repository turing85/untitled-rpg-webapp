package de.untitledrpgwebapp.boundary.persistence;

import de.untitledrpgwebapp.boundary.OrderType;
import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.boundary.persistence.exception.NoSuchAttributeException;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;

/**
 * Performs find on database with pagination and sorting.
 *
 * @param <E>
 *     the entity type.
 */
@AllArgsConstructor
public class FindAllPaged<E> {

  private final EntityManager manager;
  private final Supplier<CriteriaQuery<E>> queryProducer;
  private final Function<CriteriaQuery<E>, Root<E>> toRoot;

  /**
   * Finds all entries, obeying the pagination and sorting configuration.
   *
   * @param config
   *     pagination and sorting configuration.
   *
   * @return the paginated result.
   */
  public Collection<E> findAll(PageAndSortConfig config) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<E> query = constructJpaLanguageEntityCriteriaQuery(
        config,
        builder);
    TypedQuery<E> typedQuery = manager.createQuery(query)
        .setFirstResult(config.getOffset())
        .setMaxResults(config.getLimit());
    return typedQuery.getResultList();
  }

  private CriteriaQuery<E> constructJpaLanguageEntityCriteriaQuery(
      PageAndSortConfig config,
      CriteriaBuilder builder) {
    CriteriaQuery<E> query = queryProducer.get();
    return addOrderBy(
        query,
        config.getOrderBy(),
        OrderType.fromString(config.getOrder()),
        toRoot.apply(query),
        builder);
  }

  private CriteriaQuery<E> addOrderBy(
      CriteriaQuery<E> query,
      String orderBy,
      OrderType oderType,
      Root<E> root,
      CriteriaBuilder builder) {
    try {
      return setOrder(query, orderBy, oderType, root, builder);
    } catch (IllegalArgumentException e) {
      throw new NoSuchAttributeException(orderBy);
    }
  }

  private CriteriaQuery<E> setOrder(
      CriteriaQuery<E> query,
      String orderBy,
      OrderType oderType,
      Root<E> root,
      CriteriaBuilder builder) {
    if (Objects.isNull(orderBy)) {
      return query;
    }
    Path<Object> attributeExpression = root.get(orderBy);
    if (oderType == OrderType.ASCENDING) {
      query = query.orderBy(builder.asc(attributeExpression));
    } else if (oderType == OrderType.DESCENDING) {
      query = query.orderBy(builder.desc(attributeExpression));
    }
    return query;
  }
}


