package de.untitledrpgwebapp.user.impl.localstore.boundary.mapper;

import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserEntity;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserResponse;
import java.util.Collection;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

  CreateAccountRequest requestToRequest(CreateUserRequest request);

  UserResponse entityToRequest(UserEntity entity);

  Collection<UserResponse> entitiesToRequests(Collection<UserEntity> entities);
}
