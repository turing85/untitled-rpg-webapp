package de.untitledrpgwebapp.language.boundary.mapper;

import de.untitledrpgwebapp.language.boundary.entity.JpaLanguageEntity;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Collection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface JpaLanguageMapper {

  LanguageResponse entityToResponse(JpaLanguageEntity entity);

  Collection<LanguageResponse> entitiesToResponses(Collection<JpaLanguageEntity> entities);

  JpaLanguageEntity requestToEntity(CreateLanguageRequest request);
}
