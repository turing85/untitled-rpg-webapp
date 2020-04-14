package de.untitledrpgwebapp.user.boundary.spi.mapper;

import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.boundary.spi.entity.JpaUserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {

  JpaUserEntity requestToEntity(CreateUserRequest request);

  UserResponse entityToResponse(JpaUserEntity entity);
}
