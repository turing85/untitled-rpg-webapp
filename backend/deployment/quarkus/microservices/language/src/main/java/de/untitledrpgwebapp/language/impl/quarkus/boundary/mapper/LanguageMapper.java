package de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.CreateLanguageDto;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.LanguageDto;
import de.untitledrpgwebapp.language.impl.quarkus.entity.JpaLanguageEntity;
import java.util.Collection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface LanguageMapper {

  LanguageResponse entityToResponse(JpaLanguageEntity entity);

  LanguageDto responseToDto(LanguageResponse response);

  Collection<LanguageDto> responsesToDtos(Collection<LanguageResponse> responses);

  LanguageDto requestToResponse(CreateLanguageDto request);
}
