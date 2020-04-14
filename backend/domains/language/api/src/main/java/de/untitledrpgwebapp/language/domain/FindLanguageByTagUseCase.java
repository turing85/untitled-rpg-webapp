package de.untitledrpgwebapp.language.domain;

import de.untitledrpgwebapp.domain.UseCase;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Optional;

public interface FindLanguageByTagUseCase
    extends UseCase<FindLanguageByTagRequest, Optional<LanguageResponse>> {
}
