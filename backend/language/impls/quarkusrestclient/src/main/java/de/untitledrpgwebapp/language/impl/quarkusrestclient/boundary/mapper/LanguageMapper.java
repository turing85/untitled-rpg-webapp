package de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.mapper;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.LanguageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface LanguageMapper {

  LanguageResponse dtoToResponse(LanguageDto dto);
}
