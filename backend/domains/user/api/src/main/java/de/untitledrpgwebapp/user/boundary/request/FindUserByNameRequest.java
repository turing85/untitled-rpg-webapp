package de.untitledrpgwebapp.user.boundary.request;

import de.untitledrpgwebapp.boundary.Correlated;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindUserByNameRequest implements Correlated {

  final String name;
  final UUID correlationId;
}
