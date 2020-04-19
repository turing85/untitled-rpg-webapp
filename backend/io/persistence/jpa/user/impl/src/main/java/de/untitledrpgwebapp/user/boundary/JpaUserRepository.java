package de.untitledrpgwebapp.user.boundary;

import de.untitledrpgwebapp.user.boundary.entity.JpaUserEntity;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JpaUserRepository extends PagingAndSortingRepository<JpaUserEntity, Long> {

  Optional<JpaUserEntity> findByName(String name);

  Optional<JpaUserEntity> findByEmail(String email);
}
