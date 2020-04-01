package de.untitledrpgwebapp.domain.user.impl;

import de.untitledrpgwebapp.boundary.user.UserRepository;
import de.untitledrpgwebapp.boundary.user.mapper.UserMapper;
import de.untitledrpgwebapp.boundary.user.request.FindUserByNameRequest;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import de.untitledrpgwebapp.domain.user.FindUserByNameUseCase;
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
  public Optional<UserResponseBuilder> execute(FindUserByNameRequest request) {
    return userRepository.findByName(request)
        .map(response -> response.setCorrelationId(request.getCorrelationId()))
        .map(response -> userMapper.requestToRequest(response, request.getUserResponseBuilder()));
  }
}
