package de.untitledrpgwebapp.user.impl.quarkus.boundary.endpoint;

import de.untitledrpgwebapp.domain.exception.EntityNotFoundException;
import de.untitledrpgwebapp.impl.quarkus.boundary.request.PageConfigDto;
import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import de.untitledrpgwebapp.user.boundary.request.FindAllUsersRequest;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.domain.FindAllUsersUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.CreateUserDto;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.mapper.UserMapper;
import io.quarkus.security.Authenticated;
import java.net.URI;
import java.util.Collection;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.BeanParam;
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
@Path(UserEndpoint.PATH)
@AllArgsConstructor
public class UserEndpoint {

  public static final String PATH = "/users";
  public static final String GET_ONE_PATH_TEMPLATE = PATH + "/%s";

  private final FindAllUsersUseCase findAllUsers;
  private final FindUserByNameUseCase findUser;
  private final CreateUserUseCase createUser;
  private final UserMapper mapper;

  /**
   * This endpoint returns a collection of all users known in the system.
   *
   * @param correlationId
   *     a correlation-id for this request.
   *
   * @return A collection of all known users.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response findAll(
      @HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId,
      @Valid @BeanParam PageConfigDto config) {
    Collection<UserResponse> responses =
        findAllUsers.execute(FindAllUsersRequest.builder()
            .config(config)
            .correlationId(correlationId)
            .build());
    return Response.ok(mapper.responsesToDtos(responses))
        .header(StaticConfig.CORRELATION_ID_HEADER_KEY, correlationId)
        .build();
  }

  /**
   * Given a name, this endpoint returns the corresponding user.
   *
   * @param correlationId
   *     a correlation-id for this request.
   * @param name
   *     the name of the user to look for.
   *
   * @return the user corresponding to the name.
   */
  @GET
  @PermitAll
  @Path("/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findByName(
      @PathParam("name") @Valid @Pattern(regexp = "[a-zA-Z0-9\\-]{3,255}") String name,
      @HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId) {
    UserResponse response = findUser.execute(FindUserByNameRequest.builder()
        .name(name)
        .correlationId(correlationId)
        .build())
        .orElseThrow(() -> EntityNotFoundException.userWithName(name, correlationId));
    return Response.ok(mapper.responseToDto(response))
        .header(StaticConfig.CORRELATION_ID_HEADER_KEY, correlationId)
        .build();
  }

  /**
   * Creates a new user.
   *
   * @param dto
   *     the user to create.
   * @param correlationId
   *     the correlation-id for the process.
   *
   * @return the newly created user.
   */
  @POST
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(
      @NotNull @Valid CreateUserDto dto,
      @HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId) {
    UserResponse response = createUser.execute(mapper.dtoToRequest(dto, correlationId));
    return Response
        .created(URI.create(String.format(GET_ONE_PATH_TEMPLATE, response.getName())))
        .entity(mapper.responseToDto(response))
        .header(StaticConfig.CORRELATION_ID_HEADER_KEY, correlationId)
        .build();
  }

  /**
   * Returns information of the currently logged-in user.
   *
   * @param context
   *    the security context, holding a reference to the user principal.
   * @param correlationId
   *     the correlation-id for the process.
   *
   * @return the user corresponding to the user described in the principal.
   */
  @GET
  @Path("/me")
  @Authenticated
  @Produces(MediaType.APPLICATION_JSON)
  public Response findCurrentUser(
      @Context SecurityContext context,
      @HeaderParam(StaticConfig.CORRELATION_ID_HEADER_KEY) UUID correlationId) {
    String userName = context.getUserPrincipal().getName();
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
