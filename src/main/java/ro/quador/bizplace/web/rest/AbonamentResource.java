package ro.quador.bizplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.quador.bizplace.domain.Abonament;
import ro.quador.bizplace.repository.AbonamentRepository;
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
 * REST controller for managing Abonament.
 */
@RestController
@RequestMapping("/app")
public class AbonamentResource {

    private final Logger log = LoggerFactory.getLogger(AbonamentResource.class);

    @Inject
    private AbonamentRepository abonamentRepository;

    /**
     * POST  /rest/abonaments -> Create a new abonament.
     */
    @RequestMapping(value = "/rest/abonaments",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Abonament abonament) {
        log.debug("REST request to save Abonament : {}", abonament);
        abonamentRepository.save(abonament);
    }

    /**
     * GET  /rest/abonaments -> get all the abonaments.
     */
    @RequestMapping(value = "/rest/abonaments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Abonament> getAll() {
        log.debug("REST request to get all Abonaments");
        return abonamentRepository.findAll();
    }

    /**
     * GET  /rest/abonaments/:id -> get the "id" abonament.
     */
    @RequestMapping(value = "/rest/abonaments/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Abonament> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Abonament : {}", id);
        Abonament abonament = abonamentRepository.findOne(id);
        if (abonament == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(abonament, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/abonaments/:id -> delete the "id" abonament.
     */
    @RequestMapping(value = "/rest/abonaments/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Abonament : {}", id);
        abonamentRepository.delete(id);
    }
}
