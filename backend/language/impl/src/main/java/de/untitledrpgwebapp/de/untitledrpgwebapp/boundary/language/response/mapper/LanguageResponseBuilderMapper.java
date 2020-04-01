package de.untitledrpgwebapp.de.untitledrpgwebapp.boundary.language.response.mapper;

import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import de.untitledrpgwebapp.model.Language;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface LanguageResponseBuilderMapper {

  Language responseToEntity(LanguageResponseBuilder languageResponse);

  LanguageResponseBuilder entityToResponse(
      Language language,
      @MappingTarget LanguageResponseBuilder target);
}
