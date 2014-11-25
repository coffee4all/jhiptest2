package ro.quador.bizplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.quador.bizplace.domain.Activitate;
import ro.quador.bizplace.repository.ActivitateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Activitate.
 */
@RestController
@RequestMapping("/app")
public class ActivitateResource {

    private final Logger log = LoggerFactory.getLogger(ActivitateResource.class);

    @Inject
    private ActivitateRepository activitateRepository;

    /**
     * POST  /rest/activitates -> Create a new activitate.
     */
    @RequestMapping(value = "/rest/activitates",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Activitate activitate) {
        log.debug("REST request to save Activitate : {}", activitate);
        activitateRepository.save(activitate);
    }

    /**
     * GET  /rest/activitates -> get all the activitates.
     */
    @RequestMapping(value = "/rest/activitates",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Activitate> getAll() {
        log.debug("REST request to get all Activitates");
        return activitateRepository.findAll();
    }

    /**
     * GET  /rest/activitates/:id -> get the "id" activitate.
     */
    @RequestMapping(value = "/rest/activitates/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activitate> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Activitate : {}", id);
        Activitate activitate = activitateRepository.findOne(id);
        if (activitate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activitate, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/activitates/:id -> delete the "id" activitate.
     */
    @RequestMapping(value = "/rest/activitates/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Activitate : {}", id);
        activitateRepository.delete(id);
    }
}
