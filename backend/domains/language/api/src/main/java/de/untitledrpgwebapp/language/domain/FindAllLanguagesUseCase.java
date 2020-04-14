package de.untitledrpgwebapp.language.domain;

import de.untitledrpgwebapp.domain.UseCase;
import de.untitledrpgwebapp.language.boundary.request.FindAllLanguagesRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Collection;

public interface FindAllLanguagesUseCase
    extends UseCase<FindAllLanguagesRequest, Collection<LanguageResponse>> {
}
