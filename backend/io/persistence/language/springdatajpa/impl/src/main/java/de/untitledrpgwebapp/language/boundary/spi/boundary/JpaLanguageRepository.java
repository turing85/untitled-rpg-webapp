package de.untitledrpgwebapp.language.boundary.spi.boundary;

import de.untitledrpgwebapp.language.boundary.spi.entity.JpaLanguageEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaLanguageRepository extends CrudRepository<JpaLanguageEntity, String> {

  Optional<JpaLanguageEntity> findByTag(String tag);
}
