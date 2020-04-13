package de.untitledrpgwebapp.user.impl.quarkus.boundary.mapper;

import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.CreateUserDto;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.dto.UserDto;
import de.untitledrpgwebapp.user.impl.quarkus.entity.JpaUserEntity;
import java.util.Collection;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserMapper {

  JpaUserEntity requestToEntity(CreateUserRequest request);

  UserResponse entityToResponse(JpaUserEntity entity);

  UserDto responseToDto(UserResponse response);

  Collection<UserDto> responsesToDtos(Collection<UserResponse> responses);

  @Mapping(source = "correlationId", target = "correlationId")
  CreateUserRequest dtoToRequest(CreateUserDto dto, UUID correlationId);
}
