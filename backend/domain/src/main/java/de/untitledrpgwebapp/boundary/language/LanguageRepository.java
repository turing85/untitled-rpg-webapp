package de.untitledrpgwebapp.boundary.language;

import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import java.util.Optional;

public interface LanguageRepository {

  Optional<LanguageResponseBuilder> findByCode(String code);
}
