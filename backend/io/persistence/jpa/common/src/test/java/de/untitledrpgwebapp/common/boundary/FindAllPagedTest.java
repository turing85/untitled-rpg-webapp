package de.untitledrpgwebapp.common.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.common.boundary.exception.NoSuchAttributeException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindAllPaged unit.")
class FindAllPagedTest {

  public static final int OFFSET = 1;
  public static final int LIMIT = 2;
  public static final String ORDER_BY = "orderBy";
  private final PageAndSortConfig config = mock(PageAndSortConfig.class);

  private final EntityManager manager = mock(EntityManager.class);
  private final CriteriaBuilder builder = mock(CriteriaBuilder.class);
  private final Order order = mock(Order.class);

  @SuppressWarnings("unchecked")
  private final Supplier<CriteriaQuery<Object>> queryProducer = mock(Supplier.class);

  @SuppressWarnings("unchecked")
  private final Function<CriteriaQuery<Object>, Root<Object>> toRoot = mock(Function.class);

  @SuppressWarnings("unchecked")
  private final Root<Object> root = mock(Root.class);

  @SuppressWarnings("unchecked")
  private final Path<Object> attributeExpression = mock(Path.class);

  @SuppressWarnings("unchecked")
  private final TypedQuery<Object> typedQuery = mock(TypedQuery.class);
  private final FindAllPaged<Object> uut = new FindAllPaged<>(manager, queryProducer, toRoot);
  @SuppressWarnings("unchecked")
  CriteriaQuery<Object> query = mock(CriteriaQuery.class);

  List<Object> results = List.of(new Object(), new Object(), new Object());

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setup() {
    when(config.getOffset()).thenReturn(OFFSET);
    when(config.getLimit()).thenReturn(LIMIT);
    when(config.getOrderBy()).thenReturn(ORDER_BY);

    when(manager.getCriteriaBuilder()).thenReturn(builder);
    when(queryProducer.get()).thenReturn(query);
    when(toRoot.apply(any())).thenReturn(root);
    when(root.get(anyString())).thenReturn(attributeExpression);
    when(builder.asc(any())).thenReturn(order);
    when(builder.desc(any())).thenReturn(order);
    when(query.orderBy(any(Order.class))).thenReturn(query);
    when(manager.createQuery(any(CriteriaQuery.class))).thenReturn(typedQuery);
    when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
    when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);
    when(typedQuery.getResultList()).thenReturn(results);
  }

  @Test
  @DisplayName("Should call dependencies with expected parameters and return the expected result"
      + "with ascending order sorting when findAll is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindAllIsCalledWithAscendingOrder() {
    // GIVEN:
    when(config.getOrder()).thenReturn("ASC");

    // WHEN
    Collection<Object> actual = uut.findAll(config);

    // THEN
    assertThatResultIsAsExpected(actual);

    verifyInvocations();
    verify(builder).asc(attributeExpression);
    verify(builder, never()).desc(any());
  }

  @Test
  @DisplayName("Should call dependencies with expected parameters and return the expected result"
      + "with descending order sorting when findAll is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindAllIsCalledWithDescendingOrder() {
    // GIVEN:
    when(config.getOrder()).thenReturn("DESC");

    // WHEN
    Collection<Object> actual = uut.findAll(config);

    // THEN
    assertThatResultIsAsExpected(actual);

    verifyInvocations();
    verify(builder).desc(attributeExpression);
    verify(builder, never()).asc(any());
  }

  @Test
  @DisplayName("Should throw a NoSuchAttributeException when the order attribute is not found.")
  void shouldThrowNoSuchAttributeExceptionWhenOrderAttributeNotFound() {
    // GIVEN:
    when(root.get(anyString())).thenThrow(new IllegalArgumentException("Oops"));

    String expectedMessage = String.format(NoSuchAttributeException.MESSAGE_TEMPLATE, ORDER_BY);
    // WHEN
    NoSuchAttributeException exception = assertThrows(
        NoSuchAttributeException.class,
        () -> uut.findAll(config));

    // THEN
    assertThat(exception.getMessage(), is(expectedMessage));
  }

  private void assertThatResultIsAsExpected(Collection<Object> actual) {
    assertThat(actual, is(notNullValue()));
    assertThat(actual, hasSize(results.size()));
    assertThat(actual, containsInAnyOrder(results.toArray()));
  }

  private void verifyInvocations() {
    verify(manager).getCriteriaBuilder();
    verify(queryProducer).get();
    verify(config).getOrderBy();
    verify(config).getOrder();
    verify(toRoot).apply(query);
    verify(root).get(ORDER_BY);
    verify(query).orderBy(order);
    verify(manager).createQuery(query);
    verify(typedQuery).setFirstResult(OFFSET);
    verify(typedQuery).setMaxResults(LIMIT);
  }

}