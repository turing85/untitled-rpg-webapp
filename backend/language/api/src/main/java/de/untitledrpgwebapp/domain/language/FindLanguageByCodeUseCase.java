package de.untitledrpgwebapp.domain.language;

import de.untitledrpgwebapp.boundary.language.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import de.untitledrpgwebapp.domain.UseCase;
import java.util.Optional;

public interface FindLanguageByCodeUseCase
    extends UseCase<FindLanguageByCodeRequest, Optional<LanguageResponseBuilder>> {
}
