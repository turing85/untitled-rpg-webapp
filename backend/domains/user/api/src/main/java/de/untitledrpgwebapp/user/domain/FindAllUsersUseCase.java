package de.untitledrpgwebapp.user.domain;

import de.untitledrpgwebapp.common.domain.UseCase;
import de.untitledrpgwebapp.user.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Collection;

public interface FindAllUsersUseCase
    extends UseCase<FindAllUsersRequest, Collection<UserResponse>> {
}
