package de.untitledrpgwebapp.language.domain;

import de.untitledrpgwebapp.domain.UseCase;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Optional;

public interface FindLanguageByCodeUseCase
    extends UseCase<FindLanguageByCodeRequest, Optional<LanguageResponse>> {
}
