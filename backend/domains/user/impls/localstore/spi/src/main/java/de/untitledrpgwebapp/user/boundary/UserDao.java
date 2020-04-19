package de.untitledrpgwebapp.user.boundary;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Collection;
import java.util.Optional;

public interface UserDao {

  UserResponse save(CreateUserRequest user);

  Optional<UserResponse> findByName(String name);

  Optional<UserResponse> findByEmail(String email);

  Collection<UserResponse> findAll(PageAndSortConfig config);
}
