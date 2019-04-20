package com.btocakci.conferuns.web.rest;
import com.btocakci.conferuns.service.TalkParticipantService;
import com.btocakci.conferuns.web.rest.errors.BadRequestAlertException;
import com.btocakci.conferuns.web.rest.util.HeaderUtil;
import com.btocakci.conferuns.service.dto.TalkParticipantDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TalkParticipant.
 */
@RestController
@RequestMapping("/api")
public class TalkParticipantResource {

    private final Logger log = LoggerFactory.getLogger(TalkParticipantResource.class);

    private static final String ENTITY_NAME = "conferunsTalkParticipant";

    private final TalkParticipantService talkParticipantService;

    public TalkParticipantResource(TalkParticipantService talkParticipantService) {
        this.talkParticipantService = talkParticipantService;
    }

    /**
     * POST  /talk-participants : Create a new talkParticipant.
     *
     * @param talkParticipantDTO the talkParticipantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new talkParticipantDTO, or with status 400 (Bad Request) if the talkParticipant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/talk-participants")
    public ResponseEntity<TalkParticipantDTO> createTalkParticipant(@RequestBody TalkParticipantDTO talkParticipantDTO) throws URISyntaxException {
        log.debug("REST request to save TalkParticipant : {}", talkParticipantDTO);
        if (talkParticipantDTO.getId() != null) {
            throw new BadRequestAlertException("A new talkParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TalkParticipantDTO result = talkParticipantService.save(talkParticipantDTO);
        return ResponseEntity.created(new URI("/api/talk-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /talk-participants : Updates an existing talkParticipant.
     *
     * @param talkParticipantDTO the talkParticipantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated talkParticipantDTO,
     * or with status 400 (Bad Request) if the talkParticipantDTO is not valid,
     * or with status 500 (Internal Server Error) if the talkParticipantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/talk-participants")
    public ResponseEntity<TalkParticipantDTO> updateTalkParticipant(@RequestBody TalkParticipantDTO talkParticipantDTO) throws URISyntaxException {
        log.debug("REST request to update TalkParticipant : {}", talkParticipantDTO);
        if (talkParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TalkParticipantDTO result = talkParticipantService.save(talkParticipantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, talkParticipantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /talk-participants : get all the talkParticipants.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of talkParticipants in body
     */
    @GetMapping("/talk-participants")
    public List<TalkParticipantDTO> getAllTalkParticipants(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all TalkParticipants");
        return talkParticipantService.findAll();
    }

    /**
     * GET  /talk-participants/:id : get the "id" talkParticipant.
     *
     * @param id the id of the talkParticipantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the talkParticipantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/talk-participants/{id}")
    public ResponseEntity<TalkParticipantDTO> getTalkParticipant(@PathVariable Long id) {
        log.debug("REST request to get TalkParticipant : {}", id);
        Optional<TalkParticipantDTO> talkParticipantDTO = talkParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(talkParticipantDTO);
    }

    /**
     * DELETE  /talk-participants/:id : delete the "id" talkParticipant.
     *
     * @param id the id of the talkParticipantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/talk-participants/{id}")
    public ResponseEntity<Void> deleteTalkParticipant(@PathVariable Long id) {
        log.debug("REST request to delete TalkParticipant : {}", id);
        talkParticipantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/talk-participants?query=:query : search for the talkParticipant corresponding
     * to the query.
     *
     * @param query the query of the talkParticipant search
     * @return the result of the search
     */
    @GetMapping("/_search/talk-participants")
    public List<TalkParticipantDTO> searchTalkParticipants(@RequestParam String query) {
        log.debug("REST request to search TalkParticipants for query {}", query);
        return talkParticipantService.search(query);
    }

}
