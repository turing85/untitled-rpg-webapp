package de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.quarkus.entity.JpaLanguageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface LanguageMapper {

  LanguageResponse entityToResponse(JpaLanguageEntity entity);
}
