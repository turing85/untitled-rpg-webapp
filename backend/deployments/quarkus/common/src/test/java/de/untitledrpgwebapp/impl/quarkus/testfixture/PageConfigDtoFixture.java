package de.untitledrpgwebapp.impl.quarkus.testfixture;

import de.untitledrpgwebapp.impl.quarkus.boundary.request.PageConfigDto;

public class PageConfigDtoFixture {

  public static final int PAGE_CONFIG_DTO_PAGE = 1;
  public static final int PAGE_CONFIG_DTO_SIZE = 2;
  public static final String PAGE_CONFIG_DTO_SORT_BY = "sortBy";
  public static final String PAGE_CONFIG_DTO_ORDER = "ASC";
  public static final PageConfigDto PAGE_CONFIG_DTO = PageConfigDto.builder()
      .offset(PAGE_CONFIG_DTO_PAGE)
      .limit(PAGE_CONFIG_DTO_SIZE)
      .orderBy(PAGE_CONFIG_DTO_SORT_BY)
      .order(PAGE_CONFIG_DTO_ORDER)
      .build();

  private PageConfigDtoFixture() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
