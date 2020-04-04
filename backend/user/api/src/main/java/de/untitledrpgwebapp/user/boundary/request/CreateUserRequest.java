package de.untitledrpgwebapp.user.boundary.request;

import de.untitledrpgwebapp.boundary.Correlated;
import de.untitledrpgwebapp.user.boundary.response.CommonUser;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateUserRequest extends CommonUser implements Correlated {

  final String password;
  final UUID correlationId;
}
