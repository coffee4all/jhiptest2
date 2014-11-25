package ro.quador.bizplace.repository;

import ro.quador.bizplace.domain.Companie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Companie entity.
 */
public interface CompanieRepository extends JpaRepository<Companie, Long> {

}
