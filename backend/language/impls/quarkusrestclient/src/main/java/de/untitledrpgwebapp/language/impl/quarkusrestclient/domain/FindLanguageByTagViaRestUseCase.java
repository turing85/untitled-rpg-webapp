package de.untitledrpgwebapp.language.impl.quarkusrestclient.domain;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.mapper.LanguageMapper;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindLanguageByTagViaRestUseCase implements FindLanguageByTagUseCase {

  private LanguageRestClient restClient;
  private LanguageMapper mapper;

  @Override
  public Optional<LanguageResponse> execute(
      FindLanguageByTagRequest request) {
    return restClient.findByTag(request.getTag(), request.getCorrelationId())
        .map(mapper::dtoToResponse);
  }
}
