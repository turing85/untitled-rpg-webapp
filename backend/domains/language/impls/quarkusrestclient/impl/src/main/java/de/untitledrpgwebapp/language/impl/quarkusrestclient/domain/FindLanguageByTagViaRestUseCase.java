package de.untitledrpgwebapp.language.impl.quarkusrestclient.domain;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.mapper.LanguageMapper;
import java.util.Optional;
import java.util.UUID;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindLanguageByTagViaRestUseCase implements FindLanguageByTagUseCase {

  private final LanguageRestClient client;
  private final LanguageMapper mapper;

  @Override
  public Optional<LanguageResponse> execute(
      FindLanguageByTagRequest request) {
    UUID correlationId = request.getCorrelationId();
    try {
      return client.findByTag(request.getTag(), correlationId)
          .map(dto -> mapper.dtoToResponse(dto, correlationId));
    } catch (WebApplicationException exception) {
      if (exception.getResponse().getStatus() == Status.NOT_FOUND.getStatusCode()) {
        return Optional.empty();
      } else {
        throw exception;
      }
    }
  }
}
