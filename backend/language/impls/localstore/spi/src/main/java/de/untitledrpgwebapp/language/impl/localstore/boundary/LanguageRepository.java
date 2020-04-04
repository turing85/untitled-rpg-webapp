package de.untitledrpgwebapp.language.impl.localstore.boundary;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Optional;

public interface LanguageRepository {

  Optional<LanguageResponse> findByCode(String code);
}
