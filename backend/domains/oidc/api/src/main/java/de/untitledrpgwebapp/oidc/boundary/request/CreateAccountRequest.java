package de.untitledrpgwebapp.oidc.boundary.request;

import de.untitledrpgwebapp.common.boundary.Correlated;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CreateAccountRequest implements Correlated {

  final String name;
  final String email;
  final String password;
  final UUID correlationId;
}
