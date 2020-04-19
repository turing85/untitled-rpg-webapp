package de.untitledrpgwebapp.language.boundary;

import de.untitledrpgwebapp.common.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Collection;
import java.util.Optional;

public interface LanguageDao {

  Optional<LanguageResponse> findByTag(String tag);

  Collection<LanguageResponse> findAll(PageAndSortConfig config);

  LanguageResponse save(CreateLanguageRequest request);
}
