package de.untitledrpgwebapp.user.domain;

import de.untitledrpgwebapp.domain.UseCase;
import de.untitledrpgwebapp.user.boundary.request.FindUserByEmailRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Optional;

public interface FindUserByEmailUseCase
    extends UseCase<FindUserByEmailRequest, Optional<UserResponse>> {
}
