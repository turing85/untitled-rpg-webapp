package de.untitledrpgwebapp.user.impl.localstore.domain;

import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindAllUsersFromDatabaseUseCase implements FindAllUsersUseCase {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public Collection<UserResponse> execute(FindAllUsersRequest request) {
    return userMapper.entitiesToRequests(userRepository.findAll(request))
        .stream()
        .map(response -> response.toBuilder().correlationId(request.getCorrelationId()).build())
        .collect(Collectors.toList());
  }
}
