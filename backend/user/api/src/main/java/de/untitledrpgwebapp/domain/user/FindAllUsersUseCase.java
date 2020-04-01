package de.untitledrpgwebapp.domain.user;

import de.untitledrpgwebapp.boundary.user.request.FindAllUsersRequest;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import de.untitledrpgwebapp.domain.UseCase;
import java.util.Collection;

public interface FindAllUsersUseCase
    extends UseCase<FindAllUsersRequest, Collection<UserResponseBuilder>> {
}
