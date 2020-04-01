package de.untitledrpgwebapp.boundary.user.request.mapper;

import de.untitledrpgwebapp.boundary.user.request.SaveUserRequest;
import de.untitledrpgwebapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface SaveUserRequestMapper {

  @Mapping(source = "preferredLanguage.code", target = "preferredLanguage")
  SaveUserRequest entityToRequest(final User user, @MappingTarget SaveUserRequest target);
}
