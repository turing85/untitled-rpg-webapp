package de.untitledrpgwebapp.language.boundary.spi.mapper;

import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.boundary.spi.entity.JpaLanguageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface LanguageMapper {

  LanguageResponse entityToResponse(JpaLanguageEntity entity);

  JpaLanguageEntity requestToEntity(CreateLanguageRequest request);
}
