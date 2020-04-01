package de.untitledrpgwebapp.boundary.user.mapper;

import de.untitledrpgwebapp.boundary.auth.request.CreateAccountRequest;
import de.untitledrpgwebapp.boundary.user.request.CreateUserRequest;
import de.untitledrpgwebapp.boundary.user.response.UserBuilder;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

  CreateAccountRequest requestToRequest(
      CreateUserRequest request,
      @MappingTarget CreateAccountRequest target
  );

  UserResponseBuilder requestToRequest(
      UserBuilder saved,
      @MappingTarget UserResponseBuilder userResponseBuilder);

  /**
   * Given a {@link Collection} of {@link UserBuilder}s and a {@link Supplier} for
   * {@link UserResponseBuilder}s, this method maps each {@link UserBuilder} on a {@link
   * UserResponseBuilder}.
   *
   * @param requests
   *     to map
   * @param userResponseBuilderSupplier
   *     supplier to generate new (blank) {@link UserResponseBuilder}s
   *
   * @return the {@link Collection} of newly created {@link UserResponseBuilder}s.
   */
  default Collection<UserResponseBuilder> requestsToRequests(
      Collection<UserBuilder> requests,
      Supplier<UserResponseBuilder> userResponseBuilderSupplier) {
    return requests.stream()
        .map(request -> this.requestToRequest(request, userResponseBuilderSupplier.get()))
        .collect(Collectors.toList());

  }
}
