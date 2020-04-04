package de.untitledrpgwebapp.user.impl.localstore.domain.impl;

import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.UserRepository;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.impl.localstore.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserResponse;
import java.util.Collection;
import java.util.stream.Collectors;

public class FindAllUsersFromDatabaseUseCase implements FindAllUsersUseCase {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public FindAllUsersFromDatabaseUseCase(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public Collection<UserResponse> execute(FindAllUsersRequest request) {
    return userMapper.entitiesToRequests(userRepository.findAll(request))
        .stream()
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build())
        .collect(Collectors.toList());
  }
}
