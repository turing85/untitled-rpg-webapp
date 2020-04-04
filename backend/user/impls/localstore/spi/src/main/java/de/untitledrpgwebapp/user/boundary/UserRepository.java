package de.untitledrpgwebapp.user.boundary;

import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserEntity;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

  UserEntity save(CreateUserRequest user);

  Optional<UserEntity> findByName(FindUserByNameRequest request);

  Collection<UserEntity> findAll(FindAllUsersRequest request);
}
