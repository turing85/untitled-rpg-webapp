package de.untitledrpgwebapp.user.boundary.spi;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.boundary.persistence.FindAllPaged;
import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.boundary.spi.entity.JpaUserEntity;
import de.untitledrpgwebapp.user.boundary.spi.mapper.UserMapper;
import java.util.Collection;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApplicationScoped
@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JpaUserDao implements UserDao {

  private final JpaUserRepository repository;
  private final UserMapper mapper;
  private final FindAllPaged<JpaUserEntity> findAllPaged;

  /**
   * Bean-constructor.
   *
   * @param repository
   *     the repository to use.
   * @param mapper
   *     the mapper to use.
   * @param manager
   *     the entity manager with which the corresponding {@link FindAllPaged}-instance is created.
   */
  @Inject
  public JpaUserDao(JpaUserRepository repository, UserMapper mapper, EntityManager manager) {
    this(
        repository,
        mapper,
        new FindAllPaged<>(
            manager,
            () -> manager.getCriteriaBuilder().createQuery(JpaUserEntity.class),
            query -> query.from(JpaUserEntity.class)));
  }

  @Override
  public UserResponse save(CreateUserRequest request) {
    return mapper.entityToResponse(repository.save(mapper.requestToEntity(request)));
  }

  @Override
  public Optional<UserResponse> findByName(String name) {
    return repository.findByName(name).map(mapper::entityToResponse);
  }

  @Override
  public Optional<UserResponse> findByEmail(String email) {
    return repository.findByEmail(email).map(mapper::entityToResponse);
  }

  @Override
  public Collection<UserResponse> findAll(PageAndSortConfig config) {
    return mapper.entitiesToResponses(findAllPaged.findAll(config));
  }


}
