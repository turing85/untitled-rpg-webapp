package de.untitledrpgwebapp.domain.user;

import de.untitledrpgwebapp.boundary.user.request.CreateUserRequest;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import de.untitledrpgwebapp.domain.UseCase;

/**
 * This use case creates a new user.
 *
 * <p>During the process of user creation, the language (if present) is fetched.
 */
public interface CreateUserUseCase extends UseCase<CreateUserRequest, UserResponseBuilder> {
}
