package de.untitledrpgwebapp.user.impl.localstore.boundary.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class UserEntity extends CommonUser<UserEntity> {

  final Long id;
}
