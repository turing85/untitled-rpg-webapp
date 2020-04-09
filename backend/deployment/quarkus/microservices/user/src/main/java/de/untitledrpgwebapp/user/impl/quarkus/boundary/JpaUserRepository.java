package de.untitledrpgwebapp.user.impl.quarkus.boundary;

import de.untitledrpgwebapp.user.impl.quarkus.entity.JpaUserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepository extends CrudRepository<JpaUserEntity, Long> {

  Optional<JpaUserEntity> findByName(String name);
}
