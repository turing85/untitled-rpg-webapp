package de.untitledrpgwebapp.user.impl.localstore.domain;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.request.FindUserByEmailRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.FindUserByEmailUseCase;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindUserByEmailInDatabaseUseCase implements FindUserByEmailUseCase {

  private final UserDao dao;

  @Override
  public Optional<UserResponse> execute(FindUserByEmailRequest request) {
    return dao.findByEmail(request.getEmail())
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build());
  }
}
