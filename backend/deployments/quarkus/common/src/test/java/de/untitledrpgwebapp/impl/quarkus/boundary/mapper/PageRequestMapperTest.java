package de.untitledrpgwebapp.impl.quarkus.boundary.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.untitledrpgwebapp.impl.quarkus.boundary.request.PageConfigDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@DisplayName("Tests for PageRequestMapper unit.")
class PageRequestMapperTest {

  public static final int PAGE = 1;
  public static final int SIZE = 2;
  public static final String SORT_BY = "sortBy";

  @Test
  @DisplayName("Should construct expected PageRequest with ascending order when toPageable is "
      + "called with parameter for ascending order.")
  void shouldConstructExpectedPageRequestWithAscOrderWhenConfigToPageableIsCalledWithFittingParam() {
    // GIVEN
    String orderName = "ASC";
    PageConfigDto config = PageConfigDto.builder()
        .page(PAGE)
        .size(SIZE)
        .sortBy(SORT_BY)
        .order(orderName)
        .build();

    // WHEN
    PageRequest actual = new PageRequestMapper().configToPageable(config);

    // THEN
    assertPageRequestIsAsExpected(actual);
    Sort sort = actual.getSort();
    Order order = sort.getOrderFor(SORT_BY);
    assertThat(order, is(notNullValue()));
    assertTrue(order.isAscending());
  }

  private void assertPageRequestIsAsExpected(PageRequest pageRequest) {
    assertThat(pageRequest, is(notNullValue()));
    assertThat(pageRequest.getPageNumber(), is(PAGE));
    assertThat(pageRequest.getPageSize(), is(SIZE));
  }

  @Test
  @DisplayName("Should construct expected PageRequest with descending order when toPageable is "
      + "called with parameter for descending order.")
  void shouldConstructExpectedPageRequestWithDescOrderWhenConfigToPageableIsCalledWithFittingParam() {
    // GIVEN
    String orderName = "DESC";
    PageConfigDto config = PageConfigDto.builder()
        .page(PAGE)
        .size(SIZE)
        .sortBy(SORT_BY)
        .order(orderName)
        .build();

    // WHEN
    PageRequest actual = new PageRequestMapper().configToPageable(config);

    // THEN
    assertPageRequestIsAsExpected(actual);
    Sort sorts = actual.getSort();
    Order order = sorts.getOrderFor(SORT_BY);
    assertThat(order, is(notNullValue()));
    assertTrue(order.isDescending());
  }

  @Test
  @DisplayName("Should construct expected PageRequest without order when toPageable is called with "
      + "a parameter that does not define an order attribute.")
  void shouldConstructExpectedPageRequestWithNoOrderWhenConfigToPageableIsCalledWithFittingParam() {
    // GIVEN
    String order = "DESC";
    PageConfigDto config = PageConfigDto.builder()
        .page(PAGE)
        .size(SIZE)
        .sortBy(null)
        .order(order)
        .build();

    // WHEN
    PageRequest actual = new PageRequestMapper().configToPageable(config);

    // THEN
    assertPageRequestIsAsExpected(actual);
    assertThat(actual.getSort(), sameInstance(Sort.unsorted()));
  }

}