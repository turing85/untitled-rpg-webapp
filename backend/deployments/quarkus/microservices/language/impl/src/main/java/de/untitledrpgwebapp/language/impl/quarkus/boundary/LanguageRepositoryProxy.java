package de.untitledrpgwebapp.language.impl.quarkus.boundary;

import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper.LanguageMapper;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class LanguageRepositoryProxy implements LanguageRepository {

  private final JpaLanguageRepository repository;
  private final LanguageMapper mapper;

  @Override
  public Optional<LanguageResponse> findByTag(String tag) {
    return repository.findByTag(tag)
        .map(mapper::entityToResponse);
  }

  @Override
  public Collection<LanguageResponse> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(mapper::entityToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public LanguageResponse save(CreateLanguageRequest request) {
    return mapper.entityToResponse(repository.save(mapper.requestToEntity(request)));
  }
}
