package de.untitledrpgwebapp.user.boundary.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class UserEntity extends CommonUser {

  final Long id;
}
