package de.untitledrpgwebapp.domain.user;

import de.untitledrpgwebapp.boundary.user.request.FindUserByNameRequest;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import de.untitledrpgwebapp.domain.UseCase;
import java.util.Optional;

public interface FindUserByNameUseCase extends UseCase<FindUserByNameRequest,
    Optional<UserResponseBuilder>> {
}
