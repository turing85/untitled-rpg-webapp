package de.untitledrpgwebapp.language.boundary.spi.boundary;

import de.untitledrpgwebapp.language.boundary.spi.entity.JpaLanguageEntity;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JpaLanguageRepository
    extends PagingAndSortingRepository<JpaLanguageEntity, String> {

  Optional<JpaLanguageEntity> findByTag(String tag);
}
