package de.untitledrpgwebapp.boundary.user;

import de.untitledrpgwebapp.boundary.user.request.CreateUserRequest;
import de.untitledrpgwebapp.boundary.user.request.FindAllUsersRequest;
import de.untitledrpgwebapp.boundary.user.request.FindUserByNameRequest;
import de.untitledrpgwebapp.boundary.user.response.UserBuilder;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

  Optional<UserBuilder> save(CreateUserRequest user);

  Optional<UserBuilder> findByName(FindUserByNameRequest request);

  Collection<UserBuilder> findAll(FindAllUsersRequest request);
}
