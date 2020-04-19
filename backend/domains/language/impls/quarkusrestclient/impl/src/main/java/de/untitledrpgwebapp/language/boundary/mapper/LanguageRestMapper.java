package de.untitledrpgwebapp.language.boundary.mapper;

import de.untitledrpgwebapp.language.boundary.dto.LanguageDto;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface LanguageRestMapper {

  @Mapping(source = "correlationId", target = "correlationId")
  LanguageResponse dtoToResponse(LanguageDto dto, UUID correlationId);
}
