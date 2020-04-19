package de.untitledrpgwebapp.language.boundary;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
import de.untitledrpgwebapp.language.boundary.dto.LanguageDto;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("languages")
@RegisterRestClient(configKey = "language-rest")
public interface LanguageRestClient {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  Collection<LanguageResponse> findAllLanguages(
      @HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId);

  @GET
  @Path("/{tag}")
  @Produces(MediaType.APPLICATION_JSON)
  Optional<LanguageDto> findByTag(
      @PathParam("tag") @Valid @Pattern(regexp = "[a-z]{2}(?:-[A-Z]{2})?") String tag,
      @HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId);
}
