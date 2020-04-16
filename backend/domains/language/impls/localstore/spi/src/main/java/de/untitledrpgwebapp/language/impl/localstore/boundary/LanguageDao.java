package de.untitledrpgwebapp.language.impl.localstore.boundary;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Collection;
import java.util.Optional;

public interface LanguageDao {

  Optional<LanguageResponse> findByTag(String tag);

  Collection<LanguageResponse> findAll(PageAndSortConfig config);

  LanguageResponse save(CreateLanguageRequest request);
}
