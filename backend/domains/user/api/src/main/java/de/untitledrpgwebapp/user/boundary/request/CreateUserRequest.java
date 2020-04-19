package de.untitledrpgwebapp.user.boundary.request;

import de.untitledrpgwebapp.common.boundary.Correlated;
import de.untitledrpgwebapp.user.boundary.common.CommonUser;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CreateUserRequest extends CommonUser implements Correlated {

  final String password;
  final UUID correlationId;
}
