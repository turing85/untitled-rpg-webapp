package de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper;

import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.CreateLanguageDto;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.LanguageDto;
import java.util.Collection;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface LanguageMapper {

  LanguageDto responseToDto(LanguageResponse response);

  Collection<LanguageDto> responsesToDtos(Collection<LanguageResponse> responses);

  @Mapping(source = "correlationId", target = "correlationId")
  CreateLanguageRequest dtoToRequest(CreateLanguageDto dto, UUID correlationId);

}
