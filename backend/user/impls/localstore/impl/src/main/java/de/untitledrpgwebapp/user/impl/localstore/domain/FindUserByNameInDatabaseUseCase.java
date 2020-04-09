package de.untitledrpgwebapp.user.impl.localstore.domain;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindUserByNameInDatabaseUseCase implements FindUserByNameUseCase {

  private final UserRepository userRepository;

  @Override
  public Optional<UserResponse> execute(FindUserByNameRequest request) {
    return userRepository.findByName(request.getName())
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId())
        .build());
  }
}
