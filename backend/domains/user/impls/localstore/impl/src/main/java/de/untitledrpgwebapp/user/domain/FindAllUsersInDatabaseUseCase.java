package de.untitledrpgwebapp.user.domain;

import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindAllUsersInDatabaseUseCase implements FindAllUsersUseCase {

  private final UserDao dao;

  @Override
  public Collection<UserResponse> execute(FindAllUsersRequest request) {
    return dao.findAll(request.getConfig())
        .stream()
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build())
        .collect(Collectors.toList());
  }
}
