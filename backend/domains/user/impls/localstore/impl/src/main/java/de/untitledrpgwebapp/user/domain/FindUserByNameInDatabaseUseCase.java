package de.untitledrpgwebapp.user.domain;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindUserByNameInDatabaseUseCase implements FindUserByNameUseCase {

  private final UserDao dao;

  @Override
  public Optional<UserResponse> execute(FindUserByNameRequest request) {
    return dao.findByName(request.getName())
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build());
  }
}
