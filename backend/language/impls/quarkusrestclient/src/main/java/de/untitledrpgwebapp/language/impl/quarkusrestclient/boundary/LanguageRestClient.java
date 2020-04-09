package de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary;

import de.untitledrpgwebapp.imp.quarkus.configuration.StaticConfig;
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
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/language")
@RegisterRestClient(configKey = "language-rest")
public interface LanguageRestClient {

  @GET
  Collection<LanguageResponse> findAllLanguages(
      @HeaderParam(StaticConfig.X_CORRELATION_ID_HEADER) UUID correlationId);

  @GET
  @Path("/{tag}")
  Optional<LanguageResponse> findByTag(
      @PathParam("tag") @Valid @Pattern(regexp = "[a-z]{2}(?:-[A-Z]{2})?") String tag,
      @HeaderParam(StaticConfig.X_CORRELATION_ID_HEADER) UUID correlationId);
}
