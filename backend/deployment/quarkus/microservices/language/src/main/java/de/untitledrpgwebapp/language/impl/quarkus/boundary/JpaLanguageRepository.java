package de.untitledrpgwebapp.language.impl.quarkus.boundary;

import de.untitledrpgwebapp.language.impl.quarkus.entity.JpaLanguageEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaLanguageRepository extends CrudRepository<JpaLanguageEntity, String> {

  Optional<JpaLanguageEntity> findByTag(String tag);
}
