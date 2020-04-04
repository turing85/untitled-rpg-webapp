package de.untitledrpgwebapp.user.impl.localstore.domain.impl;

import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.UserRepository;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.impl.localstore.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserResponse;
import java.util.Optional;

public class FindUserByNameFromDatabaseUseCase implements FindUserByNameUseCase {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public FindUserByNameFromDatabaseUseCase(
      UserRepository userRepository,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public Optional<UserResponse> execute(FindUserByNameRequest request) {
    return userRepository.findByName(request)
        .map(userMapper::entityToRequest)
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build());
  }
}
