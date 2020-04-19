package de.untitledrpgwebapp.language.domain;

import de.untitledrpgwebapp.common.domain.UseCase;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;

public interface CreateLanguageUseCase extends UseCase<CreateLanguageRequest, LanguageResponse> {
}
