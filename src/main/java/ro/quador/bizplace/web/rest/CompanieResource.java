package ro.quador.bizplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.quador.bizplace.domain.Companie;
import ro.quador.bizplace.repository.CompanieRepository;
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
 * REST controller for managing Companie.
 */
@RestController
@RequestMapping("/app")
public class CompanieResource {

    private final Logger log = LoggerFactory.getLogger(CompanieResource.class);

    @Inject
    private CompanieRepository companieRepository;

    /**
     * POST  /rest/companies -> Create a new companie.
     */
    @RequestMapping(value = "/rest/companies",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Companie companie) {
        log.debug("REST request to save Companie : {}", companie);
        companieRepository.save(companie);
    }

    /**
     * GET  /rest/companies -> get all the companies.
     */
    @RequestMapping(value = "/rest/companies",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Companie> getAll() {
        log.debug("REST request to get all Companies");
        return companieRepository.findAll();
    }

    /**
     * GET  /rest/companies/:id -> get the "id" companie.
     */
    @RequestMapping(value = "/rest/companies/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Companie> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Companie : {}", id);
        Companie companie = companieRepository.findOne(id);
        if (companie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companie, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/companies/:id -> delete the "id" companie.
     */
    @RequestMapping(value = "/rest/companies/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Companie : {}", id);
        companieRepository.delete(id);
    }
}
