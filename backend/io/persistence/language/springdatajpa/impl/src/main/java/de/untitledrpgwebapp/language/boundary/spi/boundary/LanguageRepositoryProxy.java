package de.untitledrpgwebapp.language.boundary.spi.boundary;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.impl.quarkus.boundary.mapper.PageRequestMapper;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.boundary.spi.mapper.LanguageMapper;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageRepository;
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
  private final LanguageMapper languageMapper;
  private final PageRequestMapper pageRequestMapper;

  @Override
  public Collection<LanguageResponse> findAll(PageAndSortConfig config) {
    return StreamSupport
        .stream(repository.findAll(pageRequestMapper.configToPageable(config)).spliterator(), false)
        .map(languageMapper::entityToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<LanguageResponse> findByTag(String tag) {
    return repository.findByTag(tag)
        .map(languageMapper::entityToResponse);
  }

  @Override
  public LanguageResponse save(CreateLanguageRequest request) {
    return languageMapper
        .entityToResponse(repository.save(languageMapper.requestToEntity(request)));
  }
}
