package de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.mapper;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.LanguageDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface LanguageMapper {

  @Mapping(source = "correlationId", target = "correlationId")
  LanguageResponse dtoToResponse(LanguageDto dto, UUID correlationId);
}
