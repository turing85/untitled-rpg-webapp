package de.untitledrpgwebapp.language.impl.quarkus.boundary.endpoint;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.domain.FindLanguageByCodeInDatabaseUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
@Path("/languages")
public class GetLanguageEndpoint {

  private final FindLanguageByCodeInDatabaseUseCase findLanguage;

  /**
   * Given a language code, this endpoint returns the corresponding language-object.
   *
   * @param code
   *     the code of the language to look for.
   *
   * @return the language corresponding to the code.
   */
  @GET
  @Produces("application/json")
  @Path("{code}")
  public Response findByCode(@PathParam("code") String code) {
    LanguageResponse response = findLanguage
        .execute(FindLanguageByCodeRequest.builder().code(code).build())
        .orElseThrow();
    return Response.ok(response).build();
  }

}
