package de.untitledrpgwebapp.user.impl.localstore.boundary;

import de.untitledrpgwebapp.user.impl.localstore.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserEntity;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

  Optional<UserEntity> save(CreateUserRequest user);

  Optional<UserEntity> findByName(FindUserByNameRequest request);

  Collection<UserEntity> findAll(FindAllUsersRequest request);
}
