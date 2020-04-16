package de.untitledrpgwebapp.user.boundary.spi.boundary;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.impl.quarkus.boundary.mapper.PageRequestMapper;
import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.boundary.spi.mapper.UserMapper;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class UserRepositoryProxy implements UserRepository {

  private final JpaUserRepository repository;
  private final UserMapper userMapper;
  private final PageRequestMapper requestMapper;

  @Override
  public UserResponse save(CreateUserRequest request) {
    return userMapper.entityToResponse(repository.save(userMapper.requestToEntity(request)));
  }

  @Override
  public Optional<UserResponse> findByName(String name) {
    return repository.findByName(name)
        .map(userMapper::entityToResponse);
  }

  @Override
  public Collection<UserResponse> findAll(PageAndSortConfig config) {
    return StreamSupport
        .stream(repository.findAll(requestMapper.configToPageable(config)).spliterator(), false)
        .map(userMapper::entityToResponse)
        .collect(Collectors.toList());
  }
}
