package de.untitledrpgwebapp.user.boundary.request;

import de.untitledrpgwebapp.common.boundary.Correlated;
import de.untitledrpgwebapp.common.boundary.PageAndSortConfig;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindAllUsersRequest implements Correlated {

  final PageAndSortConfig config;
  final UUID correlationId;
}
