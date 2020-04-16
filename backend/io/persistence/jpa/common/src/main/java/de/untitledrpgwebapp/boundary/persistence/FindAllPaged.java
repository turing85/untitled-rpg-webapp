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
import lombok.Getter;

/**
 * Performs find on database with pagination and sorting.
 *
 * @param <E>
 *     the entity type.
 */
@AllArgsConstructor
public class FindAllPaged<E> {

  private EntityManager manager;

  private Supplier<CriteriaQuery<E>> queryProducer;

  private Function<CriteriaQuery<E>, Root<E>> toRoot;

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
    query = addOrderBy(
        query,
        config.getOrderBy(),
        OrderType.fromString(config.getOrder()),
        toRoot.apply(query),
        builder);
    return query;
  }

  private CriteriaQuery<E> addOrderBy(
      CriteriaQuery<E> query,
      String orderBy,
      OrderType oderType,
      Root<E> root,
      CriteriaBuilder builder) {
    try {
      if (Objects.nonNull(orderBy)) {
        Path<Object> attributeExpression = root.get(orderBy);
        if (oderType == OrderType.ASCENDING) {
          query = query.orderBy(builder.asc(attributeExpression));
        } else if (oderType == OrderType.DESCENDING) {
          query = query.orderBy(builder.desc(attributeExpression));
        }
      }
      return query;
    } catch (IllegalArgumentException e) {
      if (e.getMessage().startsWith("Unable to locate Attribute")) {
        throw new NoSuchAttributeException(orderBy);
      }
      throw e;
    }
  }

}
