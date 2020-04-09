package de.untitledrpgwebapp.user.impl.localstore.boundary.mapper;

import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

  CreateAccountRequest requestToRequest(CreateUserRequest request);
}
