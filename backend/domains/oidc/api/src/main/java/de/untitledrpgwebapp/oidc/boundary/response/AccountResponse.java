package de.untitledrpgwebapp.oidc.boundary.response;

import de.untitledrpgwebapp.boundary.Correlated;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AccountResponse implements Correlated {

  final String name;
  final String email;
  final UUID correlationId;
}
