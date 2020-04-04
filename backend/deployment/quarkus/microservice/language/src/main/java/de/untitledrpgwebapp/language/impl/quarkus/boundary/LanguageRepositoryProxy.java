package de.untitledrpgwebapp.language.impl.quarkus.boundary;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper.LanguageMapper;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LanguageRepositoryProxy implements LanguageRepository {

  private final JpaLanguageRepository repository;
  private final LanguageMapper mapper;

  @Inject
  public LanguageRepositoryProxy(JpaLanguageRepository repository, LanguageMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Optional<LanguageResponse> findByCode(String code) {
    return repository.findByCode(code)
        .map(mapper::entityToResponse);
  }
}
