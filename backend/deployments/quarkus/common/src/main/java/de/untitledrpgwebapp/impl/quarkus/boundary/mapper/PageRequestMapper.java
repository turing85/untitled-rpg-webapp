package de.untitledrpgwebapp.impl.quarkus.boundary.mapper;

import de.untitledrpgwebapp.boundary.OrderType;
import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ApplicationScoped
public class PageRequestMapper {

  public PageRequest configToPageable(PageAndSortConfig config) {
    return PageRequest.of(config.getOffset(), config.getLimit(), constructSort(config));
  }

  private Sort constructSort(PageAndSortConfig config) {
    if (Objects.isNull(config.getOrderBy())) {
      return Sort.unsorted();
    }
    Sort sort = Sort.by(config.getOrderBy());
    OrderType type = OrderType.fromString(config.getOrder());
    if (OrderType.ASCENDING == type) {
      sort = sort.ascending();
    } else if (OrderType.DESCENDING == type) {
      sort = sort.descending();
    }
    return sort;
  }
}