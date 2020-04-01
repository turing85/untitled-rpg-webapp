package de.untitledrpgwebapp.boundary.user;

import de.untitledrpgwebapp.boundary.user.request.SaveUserRequest;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import java.util.Optional;

public interface UserRepository {

  Optional<UserResponseBuilder> save(SaveUserRequest user);
}
