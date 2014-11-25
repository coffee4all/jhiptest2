package ro.quador.bizplace.repository;

import ro.quador.bizplace.domain.Abonament;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Abonament entity.
 */
public interface AbonamentRepository extends JpaRepository<Abonament, Long> {

}
