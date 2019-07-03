package com.btocakci.conferuns.web.rest;
import com.btocakci.conferuns.service.ConferenceService;
import com.btocakci.conferuns.web.rest.errors.BadRequestAlertException;
import com.btocakci.conferuns.web.rest.util.HeaderUtil;
import com.btocakci.conferuns.service.dto.ConferenceDTO;
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
 * REST controller for managing Conference.
 */
@RestController
@RequestMapping("/api")
public class ConferenceController {

    private final Logger log = LoggerFactory.getLogger(ConferenceController.class);

    private static final String ENTITY_NAME = "conferunsConference";

    private final ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    /**
     * POST  /conferences : Create a new conference.
     *
     * @param conferenceDTO the conferenceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conferenceDTO, or with status 400 (Bad Request) if the conference has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conferences")
    public ResponseEntity<ConferenceDTO> createConference(@RequestBody ConferenceDTO conferenceDTO) throws URISyntaxException {
        log.debug("REST request to save Conference : {}", conferenceDTO);
        if (conferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new conference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConferenceDTO result = conferenceService.save(conferenceDTO);
        return ResponseEntity.created(new URI("/api/conferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conferences : Updates an existing conference.
     *
     * @param conferenceDTO the conferenceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conferenceDTO,
     * or with status 400 (Bad Request) if the conferenceDTO is not valid,
     * or with status 500 (Internal Server Error) if the conferenceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conferences")
    public ResponseEntity<ConferenceDTO> updateConference(@RequestBody ConferenceDTO conferenceDTO) throws URISyntaxException {
        log.debug("REST request to update Conference : {}", conferenceDTO);
        if (conferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConferenceDTO result = conferenceService.save(conferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conferences : get all the conferences.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of conferences in body
     */
    @GetMapping("/conferences")
    public List<ConferenceDTO> getAllConferences(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Conferences");
        return conferenceService.findAll();
    }

    /**
     * GET  /conferences/:id : get the "id" conference.
     *
     * @param id the id of the conferenceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conferenceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/conferences/{id}")
    public ResponseEntity<ConferenceDTO> getConference(@PathVariable Long id) {
        log.debug("REST request to get Conference : {}", id);
        Optional<ConferenceDTO> conferenceDTO = conferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conferenceDTO);
    }

    /**
     * DELETE  /conferences/:id : delete the "id" conference.
     *
     * @param id the id of the conferenceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conferences/{id}")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        log.debug("REST request to delete Conference : {}", id);
        conferenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/conferences?query=:query : search for the conference corresponding
     * to the query.
     *
     * @param query the query of the conference search
     * @return the result of the search
     */
    @GetMapping("/_search/conferences")
    public List<ConferenceDTO> searchConferences(@RequestParam String query) {
        log.debug("REST request to search Conferences for query {}", query);
        return conferenceService.search(query);
    }

}