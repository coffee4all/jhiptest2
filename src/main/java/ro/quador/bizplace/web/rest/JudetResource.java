package ro.quador.bizplace.web.rest;

import com.codahale.metrics.annotation.Timed;

import ro.quador.bizplace.domain.Judet;
import ro.quador.bizplace.repository.JudetRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * REST controller for managing Judet.
 */
@RestController
@RequestMapping("/app")
public class JudetResource {

    private final Logger log = LoggerFactory.getLogger(JudetResource.class);

    @Inject
    private JudetRepository judetRepository;

    /**
     * POST  /rest/judets -> Create a new judet.
     */
    @RequestMapping(value = "/rest/judets",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Judet judet) {
        log.debug("REST request to save Judet : {}", judet);
        judetRepository.save(judet);
    }

    /**
     * GET  /rest/judets -> get all the judets.
     */
    @RequestMapping(value = "/rest/judets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Judet> getAll() {
        log.debug("REST request to get all Judets");
        return judetRepository.findAll();
    }

    /**
     * GET  /rest/judets/:id -> get the "id" judet.
     */
    @RequestMapping(value = "/rest/judets/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Judet> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Judet : {}", id);
        Judet judet = judetRepository.findOne(id);
        if (judet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(judet, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/judets/:id -> delete the "id" judet.
     */
    @RequestMapping(value = "/rest/judets/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Judet : {}", id);
        judetRepository.delete(id);
    }
}
