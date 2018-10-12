package fr.eai.application.rnd.management.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.eai.application.rnd.management.domain.Sujet;
import fr.eai.application.rnd.management.repository.SujetRepository;
import fr.eai.application.rnd.management.web.rest.errors.BadRequestAlertException;
import fr.eai.application.rnd.management.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sujet.
 */
@RestController
@RequestMapping("/api")
public class SujetResource {

    private final Logger log = LoggerFactory.getLogger(SujetResource.class);

    private static final String ENTITY_NAME = "sujet";

    private final SujetRepository sujetRepository;

    public SujetResource(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }

    /**
     * POST  /sujets : Create a new sujet.
     *
     * @param sujet the sujet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sujet, or with status 400 (Bad Request) if the sujet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sujets")
    @Timed
    public ResponseEntity<Sujet> createSujet(@RequestBody Sujet sujet) throws URISyntaxException {
        log.debug("REST request to save Sujet : {}", sujet);
        if (sujet.getId() != null) {
            throw new BadRequestAlertException("A new sujet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sujet result = sujetRepository.save(sujet);
        return ResponseEntity.created(new URI("/api/sujets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sujets : Updates an existing sujet.
     *
     * @param sujet the sujet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sujet,
     * or with status 400 (Bad Request) if the sujet is not valid,
     * or with status 500 (Internal Server Error) if the sujet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sujets")
    @Timed
    public ResponseEntity<Sujet> updateSujet(@RequestBody Sujet sujet) throws URISyntaxException {
        log.debug("REST request to update Sujet : {}", sujet);
        if (sujet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sujet result = sujetRepository.save(sujet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sujet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sujets : get all the sujets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sujets in body
     */
    @GetMapping("/sujets")
    @Timed
    public List<Sujet> getAllSujets() {
        log.debug("REST request to get all Sujets");
        return sujetRepository.findAll();
    }

    /**
     * GET  /sujets/:id : get the "id" sujet.
     *
     * @param id the id of the sujet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sujet, or with status 404 (Not Found)
     */
    @GetMapping("/sujets/{id}")
    @Timed
    public ResponseEntity<Sujet> getSujet(@PathVariable Long id) {
        log.debug("REST request to get Sujet : {}", id);
        Optional<Sujet> sujet = sujetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sujet);
    }

    /**
     * DELETE  /sujets/:id : delete the "id" sujet.
     *
     * @param id the id of the sujet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sujets/{id}")
    @Timed
    public ResponseEntity<Void> deleteSujet(@PathVariable Long id) {
        log.debug("REST request to delete Sujet : {}", id);

        sujetRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
