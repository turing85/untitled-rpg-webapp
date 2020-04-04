package de.untitledrpgwebapp.language.impl.quarkus.boundary;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper.LanguageMapper;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class LanguageRepositoryProxy implements LanguageRepository {

  private final JpaLanguageRepository repository;
  private final LanguageMapper mapper;

  @Override
  public Optional<LanguageResponse> findByCode(String code) {
    return repository.findByCode(code)
        .map(mapper::entityToResponse);
  }
}
