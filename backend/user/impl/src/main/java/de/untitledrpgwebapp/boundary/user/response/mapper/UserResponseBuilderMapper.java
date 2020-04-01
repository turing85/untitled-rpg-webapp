package de.untitledrpgwebapp.boundary.user.response.mapper;

import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import de.untitledrpgwebapp.de.untitledrpgwebapp.boundary.language.response.mapper.LanguageResponseBuilderMapper;
import de.untitledrpgwebapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {LanguageResponseBuilderMapper.class})
public interface UserResponseBuilderMapper {

  @Mapping(target = "preferredLanguage", ignore = true)
  User responseToEntity(UserResponseBuilder userResponseBuilder);

  @Mapping(source = "preferredLanguage.code", target = "preferredLanguage")
  void entityToResponse(User user, @MappingTarget UserResponseBuilder target);
}
