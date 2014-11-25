package ro.quador.bizplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.quador.bizplace.domain.Tipabonament;
import ro.quador.bizplace.repository.TipabonamentRepository;
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
 * REST controller for managing Tipabonament.
 */
@RestController
@RequestMapping("/app")
public class TipabonamentResource {

    private final Logger log = LoggerFactory.getLogger(TipabonamentResource.class);

    @Inject
    private TipabonamentRepository tipabonamentRepository;

    /**
     * POST  /rest/tipabonaments -> Create a new tipabonament.
     */
    @RequestMapping(value = "/rest/tipabonaments",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Tipabonament tipabonament) {
        log.debug("REST request to save Tipabonament : {}", tipabonament);
        tipabonamentRepository.save(tipabonament);
    }

    /**
     * GET  /rest/tipabonaments -> get all the tipabonaments.
     */
    @RequestMapping(value = "/rest/tipabonaments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Tipabonament> getAll() {
        log.debug("REST request to get all Tipabonaments");
        return tipabonamentRepository.findAll();
    }

    /**
     * GET  /rest/tipabonaments/:id -> get the "id" tipabonament.
     */
    @RequestMapping(value = "/rest/tipabonaments/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tipabonament> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Tipabonament : {}", id);
        Tipabonament tipabonament = tipabonamentRepository.findOne(id);
        if (tipabonament == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipabonament, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/tipabonaments/:id -> delete the "id" tipabonament.
     */
    @RequestMapping(value = "/rest/tipabonaments/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Tipabonament : {}", id);
        tipabonamentRepository.delete(id);
    }
}
