package de.untitledrpgwebapp.user.boundary.mapper;

import de.untitledrpgwebapp.user.boundary.entity.JpaUserEntity;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Collection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface JpaUserMapper {

  JpaUserEntity requestToEntity(CreateUserRequest request);

  UserResponse entityToResponse(JpaUserEntity entity);

  Collection<UserResponse> entitiesToResponses(Collection<JpaUserEntity> entities);
}
