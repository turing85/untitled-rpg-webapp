package de.untitledrpgwebapp.user.domain;

import de.untitledrpgwebapp.domain.UseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserResponse;
import java.util.Collection;

public interface FindAllUsersUseCase
    extends UseCase<FindAllUsersRequest, Collection<UserResponse>> {
}
