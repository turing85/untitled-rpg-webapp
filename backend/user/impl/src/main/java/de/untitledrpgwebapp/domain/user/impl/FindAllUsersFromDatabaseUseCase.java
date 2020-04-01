package de.untitledrpgwebapp.domain.user.impl;

import de.untitledrpgwebapp.boundary.user.UserRepository;
import de.untitledrpgwebapp.boundary.user.mapper.UserMapper;
import de.untitledrpgwebapp.boundary.user.request.FindAllUsersRequest;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import de.untitledrpgwebapp.domain.user.FindAllUsersUseCase;
import java.util.Collection;

public class FindAllUsersFromDatabaseUseCase implements FindAllUsersUseCase {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public FindAllUsersFromDatabaseUseCase(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public Collection<UserResponseBuilder> execute(FindAllUsersRequest request) {
    return userMapper.requestsToRequests(
        userRepository.findAll(request),
        request::createUserResponseBuilder);
  }
}
