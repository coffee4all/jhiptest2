package ro.quador.bizplace.repository;

import ro.quador.bizplace.domain.Activitate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Activitate entity.
 */
public interface ActivitateRepository extends JpaRepository<Activitate, Long> {

}
