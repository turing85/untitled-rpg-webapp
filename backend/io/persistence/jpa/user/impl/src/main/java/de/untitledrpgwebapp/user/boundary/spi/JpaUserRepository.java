package de.untitledrpgwebapp.user.boundary.spi;

import de.untitledrpgwebapp.user.boundary.spi.entity.JpaUserEntity;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JpaUserRepository extends PagingAndSortingRepository<JpaUserEntity, Long> {

  Optional<JpaUserEntity> findByName(String name);
}
