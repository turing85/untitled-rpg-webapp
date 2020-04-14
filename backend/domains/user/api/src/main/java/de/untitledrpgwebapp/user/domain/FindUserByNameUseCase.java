package de.untitledrpgwebapp.user.domain;

import de.untitledrpgwebapp.domain.UseCase;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Optional;

public interface FindUserByNameUseCase
    extends UseCase<FindUserByNameRequest, Optional<UserResponse>> {
}
