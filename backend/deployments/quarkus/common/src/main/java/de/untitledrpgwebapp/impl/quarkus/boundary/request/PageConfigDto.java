package de.untitledrpgwebapp.impl.quarkus.boundary.request;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class PageConfigDto implements PageAndSortConfig {

  @Min(0)
  @QueryParam("page")
  @DefaultValue("0")
  private int page;

  @Min(1)
  @QueryParam("size")
  @DefaultValue(StaticConfig.DEFAULT_PAGINATION_SIZE)
  private int size;

  @QueryParam("sortBy")
  private String sortBy;

  @QueryParam("order")
  @DefaultValue(StaticConfig.DEFAULT_PAGINATION_ORDER)
  private String order;
}
