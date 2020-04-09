package de.untitledrpgwebapp.user.impl.quarkus.boundary.endpoint;

import de.untitledrpgwebapp.imp.quarkus.configuration.StaticConfig;
import de.untitledrpgwebapp.user.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.CreateUserDto;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.mapper.UserMapper;
import java.util.Collection;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@ApplicationScoped
@Path("/users")
@AllArgsConstructor
public class UserEndpoint {

  private final CreateUserUseCase createUser;
  private final FindAllUsersUseCase findAllUsers;
  private final FindUserByNameUseCase findUser;
  private final UserMapper mapper;

  @GET
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON)
  public Response findAllUsers(
      @HeaderParam(StaticConfig.X_CORRELATION_ID_HEADER) UUID correlationId) {
    Collection<UserResponse> responses =
        findAllUsers.execute(FindAllUsersRequest.builder().correlationId(correlationId).build());
    return Response.ok(mapper.responsesToDtos(responses))
        .header(StaticConfig.X_CORRELATION_ID_HEADER, correlationId)
        .build();
  }

  @GET
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{name}")
  public Response findUserByName(
      @PathParam("name") @Valid @Pattern(regexp = "[a-zA-Z0-9\\-]{3,255}") String name,
      @HeaderParam(StaticConfig.X_CORRELATION_ID_HEADER) UUID correlationId) {
    UserResponse response = findUser.execute(FindUserByNameRequest.builder()
        .name(name)
        .correlationId(correlationId)
        .build())
        // TODO: throw a proper exception here
        .orElseThrow();
    return Response.ok(mapper.responseToDto(response))
        .header(StaticConfig.X_CORRELATION_ID_HEADER, correlationId)
        .build();
  }

  @POST
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(
      @Valid CreateUserDto httpRequest,
      @HeaderParam(StaticConfig.X_CORRELATION_ID_HEADER) UUID correlationId) {
    UserResponse response = createUser.execute(
        mapper.dtoToRequest(httpRequest)
            .toBuilder()
            .correlationId(correlationId)
            .build());
    return Response.ok(mapper.responseToDto(response))
        .header(StaticConfig.X_CORRELATION_ID_HEADER, correlationId)
        .build();
  }
}
