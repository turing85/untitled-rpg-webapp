package de.untitledrpgwebapp.user.domain;

import de.untitledrpgwebapp.common.domain.UseCase;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;

/**
 * This use case creates a new user.
 *
 * <p>During the process of user creation, the language (if present) is fetched.
 */
public interface CreateUserUseCase extends UseCase<CreateUserRequest, UserResponse> {
}
