package ro.quador.bizplace.repository;

import ro.quador.bizplace.domain.Tipabonament;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Tipabonament entity.
 */
public interface TipabonamentRepository extends JpaRepository<Tipabonament, Long> {

}
