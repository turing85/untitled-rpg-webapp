package de.untitledrpgwebapp.language.impl.quarkusrestclient.domain;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.mapper.LanguageMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindLanguageByTagViaRestUseCase implements FindLanguageByTagUseCase {

  private LanguageRestClient client;
  private LanguageMapper mapper;

  @Override
  public Optional<LanguageResponse> execute(
      FindLanguageByTagRequest request) {
    UUID correlationId = request.getCorrelationId();
    return client.findByTag(request.getTag(), correlationId)
        .map(dto -> mapper.dtoToResponse(dto, correlationId));
  }
}
