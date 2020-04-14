package de.untitledrpgwebapp.user.boundary.spi.boundary;

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
  private final UserMapper mapper;

  @Override
  public UserResponse save(CreateUserRequest request) {
    return mapper.entityToResponse(repository.save(mapper.requestToEntity(request)));
  }

  @Override
  public Optional<UserResponse> findByName(String name) {
    return repository.findByName(name)
        .map(mapper::entityToResponse);
  }

  @Override
  public Collection<UserResponse> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(mapper::entityToResponse)
        .collect(Collectors.toList());
  }
}
