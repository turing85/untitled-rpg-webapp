package de.untitledrpgwebapp.language.boundary.mapper;

import de.untitledrpgwebapp.language.boundary.dto.CreateLanguageDto;
import de.untitledrpgwebapp.language.boundary.dto.LanguageDto;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Collection;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface DeploymentLanguageMapper {

  LanguageDto responseToDto(LanguageResponse response);

  Collection<LanguageDto> responsesToDtos(Collection<LanguageResponse> responses);

  @Mapping(source = "correlationId", target = "correlationId")
  CreateLanguageRequest dtoToRequest(CreateLanguageDto dto, UUID correlationId);

}
