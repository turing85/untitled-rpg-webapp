package de.untitledrpgwebapp.impl.quarkus.boundary.mapper;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.boundary.SortType;
import java.util.Objects;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ApplicationScoped
public class PageRequestMapper {

  public PageRequest configToPageable(PageAndSortConfig config) {
    return PageRequest.of(config.getPage(), config.getSize(), constructSort(config));
  }

  private Sort constructSort(PageAndSortConfig config) {
    if (Objects.isNull(config.getSortBy())) {
      return Sort.unsorted();
    }
    Sort sort = Sort.by(config.getSortBy());
    Optional<SortType> converted = SortType.fromString(config.getOrder());
    if (converted.isPresent()) {
      SortType type = converted.get();
      if (SortType.ASCENDING == type) {
        sort = sort.ascending();
      } else if (SortType.DESCENDING == type) {
        sort = sort.descending();
      }
    }
    return sort;
  }
}