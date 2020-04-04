package de.untitledrpgwebapp.user.impl.localstore.boundary.request;

import de.untitledrpgwebapp.user.impl.localstore.boundary.response.CommonUser;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateUserRequest extends CommonUser<CreateUserRequest> {

  final String password;
}
