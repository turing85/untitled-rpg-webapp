package de.untitledrpgwebapp.boundary.user.request.mapper;

import de.untitledrpgwebapp.boundary.user.request.CreateUserRequest;
import de.untitledrpgwebapp.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface CreateUserRequestMapper {

  User requestToUser(final CreateUserRequest request);
}
