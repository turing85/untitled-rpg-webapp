package de.untitledrpgwebapp.language.boundary.spi.boundary;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.boundary.persistence.FindAllPaged;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.boundary.spi.entity.JpaLanguageEntity;
import de.untitledrpgwebapp.language.boundary.spi.mapper.LanguageMapper;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
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
public class JpaLanguageDao implements LanguageDao {

  private final JpaLanguageRepository repository;
  private final LanguageMapper mapper;
  private final FindAllPaged<JpaLanguageEntity> findAllPaged;

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
  public JpaLanguageDao(
      JpaLanguageRepository repository,
      LanguageMapper mapper,
      EntityManager manager) {
    this(
        repository,
        mapper,
        new FindAllPaged<>(
            manager,
            () -> manager.getCriteriaBuilder().createQuery(JpaLanguageEntity.class),
            query -> query.from(JpaLanguageEntity.class)));
  }

  @Override
  public Optional<LanguageResponse> findByTag(String tag) {
    return repository.findByTag(tag)
        .map(mapper::entityToResponse);
  }

  @Override
  public LanguageResponse save(CreateLanguageRequest request) {
    return mapper
        .entityToResponse(repository.save(mapper.requestToEntity(request)));
  }

  @Override
  public Collection<LanguageResponse> findAll(PageAndSortConfig config) {
    return mapper.entitiesToResponses(findAllPaged.findAll(config));
  }
}
