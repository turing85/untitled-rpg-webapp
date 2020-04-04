package de.untitledrpgwebapp.user.impl.localstore.domain;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindUserByNameFromDatabaseUseCase implements FindUserByNameUseCase {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public Optional<UserResponse> execute(FindUserByNameRequest request) {
    return userRepository.findByName(request)
        .map(userMapper::entityToResponse)
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build());
  }
}
