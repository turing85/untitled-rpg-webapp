package de.untitledrpgwebapp.language.impl.quarkus.boundary.endpoint;

import de.untitledrpgwebapp.imp.quarkus.configuration.StaticConfig;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.domain.FindLanguageByTagInDatabaseUseCase;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@ApplicationScoped
@Slf4j
@AllArgsConstructor
@Path("/languages")
public class GetLanguageEndpoint {

  private final FindLanguageByTagInDatabaseUseCase findLanguage;
  private final Logger logger;

  /**
   * Given a language tag, this endpoint returns the corresponding language-object.
   *
   * @param tag
   *     the tag of the language to look for.
   *
   * @return the language corresponding to the tag.
   */
  @GET
  @Produces("application/json")
  @PermitAll
  @Path("{tag}")
  public Response findByTag(
      @PathParam("tag") String tag,
      @HeaderParam (StaticConfig.X_CORRELATION_ID_HEADER) UUID correlationId,
      @Context SecurityContext context) {
    if (logger.isInfoEnabled()) {
      logger.info("CorrelationId: {}", correlationId);
    }
    LanguageResponse response = findLanguage
        .execute(FindLanguageByTagRequest.builder().tag(tag).correlationId(correlationId).build())
        .orElseThrow();
    return Response.
        ok(response)
        .header(StaticConfig.X_CORRELATION_ID_HEADER, response.getCorrelationId())
        .build();
  }

}
