package de.untitledrpgwebapp.user.boundary.spi.boundary;

import de.untitledrpgwebapp.user.boundary.spi.entity.JpaUserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepository extends CrudRepository<JpaUserEntity, Long> {

  Optional<JpaUserEntity> findByName(String name);
}
