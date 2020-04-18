package de.untitledrpgwebapp.user.impl.quarkus.boundary.endpoint;

import de.untitledrpgwebapp.domain.exception.EntityNotFoundException;
import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.mapper.UserMapper;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@RequestScoped
@Path(UserEndpoint.PATH + "/me")
@AllArgsConstructor
public class MeEndpoint {

  private final FindUserByNameUseCase findUser;
  private final SecurityIdentity identity;
  private final UserMapper mapper;


  /**
   * Returns information of the currently logged-in user.
   *
   * @param correlationId
   *     the correlation-id for the process.
   *
   * @return the user corresponding to the user described in the principal.
   */
  @GET
  @Authenticated
  @Produces(MediaType.APPLICATION_JSON)
  public Response findCurrentUser(
      @HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId) {
    String userName = identity.getPrincipal().getName();
    UserResponse user =
        findUser.execute(FindUserByNameRequest.builder()
            .name(userName)
            .correlationId(correlationId).build()
        ).orElseThrow(() -> EntityNotFoundException.userWithName(userName, correlationId));
    return Response.ok(mapper.responseToDto(user))
        .header(StaticConfig.CORRELATION_ID_HEADER_KEY, correlationId)
        .build();
  }
}
