package de.untitledrpgwebapp.language.impl.quarkus.boundary.endpoint;

import de.untitledrpgwebapp.imp.quarkus.configuration.StaticConfig;
import de.untitledrpgwebapp.language.boundary.request.FindAllLanguagesRequest;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.CreateLanguageDto;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper.LanguageMapper;
import java.util.Collection;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import lombok.AllArgsConstructor;

@ApplicationScoped
@Path("/languages")
@AllArgsConstructor
public class LanguageEndpoint {

  private final FindAllLanguagesUseCase findAllLanguages;
  private final FindLanguageByTagUseCase findLanguage;
  private final LanguageMapper mapper;

  /**
   * This endpoint returns a collection of all languages known in the system.
   *
   * @param correlationId
   *     a correlation id for this request.
   *
   * @return A collection of all known languages.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @PermitAll
  public Response findAll(@HeaderParam(StaticConfig.X_CORRELATION_ID_HEADER) UUID correlationId) {
    Collection<LanguageResponse> responses = findAllLanguages.execute(
        FindAllLanguagesRequest.builder()
            .correlationId(correlationId)
            .build());
    return Response
        .ok(mapper.responsesToDtos(responses))
        .header(StaticConfig.X_CORRELATION_ID_HEADER, correlationId)
        .build();
  }

  /**
   * Given a language tag, this endpoint returns the corresponding language-object.
   *
   * @param correlationId
   *     a correlation-id for this request.
   * @param tag
   *     the tag of the language to look for.
   *
   * @return the language corresponding to the tag.
   */
  @GET
  @Path("/{tag}")
  @Produces(MediaType.APPLICATION_JSON)
  @PermitAll
  public Response findByTag(
      @PathParam("tag") @Valid @Pattern(regexp = "[a-z]{2}(?:-[A-Z]{2})?") String tag,
      @HeaderParam(StaticConfig.X_CORRELATION_ID_HEADER) UUID correlationId,
      @Context SecurityContext context) {
    LanguageResponse response = findLanguage
        .execute(FindLanguageByTagRequest.builder().tag(tag).correlationId(correlationId).build())
        .orElseThrow();
    return Response
        .ok(mapper.responseToDto(response))
        .header(StaticConfig.X_CORRELATION_ID_HEADER, correlationId)
        .build();
  }

  /**
   * Creates a new language.
   *
   * @param httpRequest
   *     the language to create.
   * @param correlationId
   *     the correlation-id for the process.
   *
   * @return the newly created language.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @PermitAll
  public Response test(
      @NotNull @Valid CreateLanguageDto httpRequest,
      @HeaderParam(StaticConfig.X_CORRELATION_ID_HEADER) UUID correlationId) {
    return Response.ok(mapper.requestToResponse(httpRequest))
        .header(StaticConfig.X_CORRELATION_ID_HEADER, correlationId)
        .build();
  }

}

