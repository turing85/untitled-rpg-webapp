package de.untitledrpgwebapp.language.impl.quarkus.boundary.endpoint;

import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import de.untitledrpgwebapp.language.boundary.request.FindAllLanguagesRequest;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.CreateLanguageDto;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper.LanguageMapper;
import java.net.URI;
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
@Path(LanguageEndpoint.PATH)
@AllArgsConstructor
public class LanguageEndpoint {

  public static final String PATH = "/languages";
  public static final String GET_ONE_PATH_TEMPLATE = PATH + "/%s";

  private final FindAllLanguagesUseCase findAllLanguages;
  private final FindLanguageByTagUseCase findLanguage;
  private final CreateLanguageUseCase createLanguage;
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
  public Response findAll(@HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId) {
    Collection<LanguageResponse> responses = findAllLanguages.execute(
        FindAllLanguagesRequest.builder()
            .correlationId(correlationId)
            .build());
    return Response
        .ok(mapper.responsesToDtos(responses))
        .header(StaticConfig.CORRELATION_ID_HEADER_KEY, correlationId)
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
      @HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId,
      @Context SecurityContext context) {
    LanguageResponse response = findLanguage
        .execute(FindLanguageByTagRequest.builder().tag(tag).correlationId(correlationId).build())
        .orElseThrow();
    return Response
        .ok(mapper.responseToDto(response))
        .header(StaticConfig.CORRELATION_ID_HEADER_KEY, correlationId)
        .build();
  }

  /**
   * Creates a new language.
   *
   * @param dto
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
  public Response createLanguage(
      @NotNull @Valid CreateLanguageDto dto,
      @HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId) {
    LanguageResponse created = createLanguage
        .execute(mapper.dtoToRequest(dto).toBuilder()
            .correlationId(correlationId)
            .build());
    return Response.created(URI.create(String.format(GET_ONE_PATH_TEMPLATE, created.getTag())))
        .entity(mapper.responseToDto(created))
        .header(StaticConfig.CORRELATION_ID_HEADER_KEY, correlationId)
        .build();
  }
}

