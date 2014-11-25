package ro.quador.bizplace.repository;

import ro.quador.bizplace.domain.Judet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Judet entity.
 */
public interface JudetRepository extends JpaRepository<Judet, Long> {

}
