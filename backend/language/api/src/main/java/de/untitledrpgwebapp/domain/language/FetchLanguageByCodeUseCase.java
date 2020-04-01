package de.untitledrpgwebapp.domain.language;

import de.untitledrpgwebapp.boundary.language.request.FetchLanguageByCodeRequest;
import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import de.untitledrpgwebapp.domain.UseCase;
import java.util.Optional;

public interface FetchLanguageByCodeUseCase
    extends UseCase<FetchLanguageByCodeRequest, Optional<LanguageResponseBuilder>> {
}
