package de.untitledrpgwebapp.user.boundary.response;

import de.untitledrpgwebapp.boundary.Correlated;
import de.untitledrpgwebapp.user.boundary.common.CommonUser;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class UserResponse extends CommonUser implements Correlated {

  final UUID correlationId;
}
