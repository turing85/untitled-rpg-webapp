package de.untitledrpgwebapp.user.boundary;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

  UserResponse save(CreateUserRequest user);

  Optional<UserResponse> findByName(String name);

  Collection<UserResponse> findAll(PageAndSortConfig config);
}
