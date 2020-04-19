package de.untitledrpgwebapp.user.boundary.mapper;

import de.untitledrpgwebapp.user.boundary.dto.CreateUserDto;
import de.untitledrpgwebapp.user.boundary.dto.UserDto;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Collection;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface DeploymentUserMapper {

  UserDto responseToDto(UserResponse response);

  Collection<UserDto> responsesToDtos(Collection<UserResponse> responses);

  @Mapping(source = "correlationId", target = "correlationId")
  CreateUserRequest dtoToRequest(CreateUserDto dto, UUID correlationId);
}
