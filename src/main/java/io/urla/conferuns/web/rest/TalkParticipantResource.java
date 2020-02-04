package io.urla.conferuns.web.rest;

import io.urla.conferuns.service.TalkParticipantService;
import io.urla.conferuns.web.rest.errors.BadRequestAlertException;
import io.urla.conferuns.service.dto.TalkParticipantDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.urla.conferuns.domain.TalkParticipant}.
 */
@RestController
@RequestMapping("/api")
public class TalkParticipantResource {

    private final Logger log = LoggerFactory.getLogger(TalkParticipantResource.class);

    private static final String ENTITY_NAME = "talkParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TalkParticipantService talkParticipantService;

    public TalkParticipantResource(TalkParticipantService talkParticipantService) {
        this.talkParticipantService = talkParticipantService;
    }

    /**
     * {@code POST  /talk-participants} : Create a new talkParticipant.
     *
     * @param talkParticipantDTO the talkParticipantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new talkParticipantDTO, or with status {@code 400 (Bad Request)} if the talkParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/talk-participants")
    public ResponseEntity<TalkParticipantDTO> createTalkParticipant(@RequestBody TalkParticipantDTO talkParticipantDTO) throws URISyntaxException {
        log.debug("REST request to save TalkParticipant : {}", talkParticipantDTO);
        if (talkParticipantDTO.getId() != null) {
            throw new BadRequestAlertException("A new talkParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TalkParticipantDTO result = talkParticipantService.save(talkParticipantDTO);
        return ResponseEntity.created(new URI("/api/talk-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /talk-participants} : Updates an existing talkParticipant.
     *
     * @param talkParticipantDTO the talkParticipantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated talkParticipantDTO,
     * or with status {@code 400 (Bad Request)} if the talkParticipantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the talkParticipantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/talk-participants")
    public ResponseEntity<TalkParticipantDTO> updateTalkParticipant(@RequestBody TalkParticipantDTO talkParticipantDTO) throws URISyntaxException {
        log.debug("REST request to update TalkParticipant : {}", talkParticipantDTO);
        if (talkParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TalkParticipantDTO result = talkParticipantService.save(talkParticipantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, talkParticipantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /talk-participants} : get all the talkParticipants.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of talkParticipants in body.
     */
    @GetMapping("/talk-participants")
    public List<TalkParticipantDTO> getAllTalkParticipants(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all TalkParticipants");
        return talkParticipantService.findAll();
    }

    /**
     * {@code GET  /talk-participants/:id} : get the "id" talkParticipant.
     *
     * @param id the id of the talkParticipantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the talkParticipantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/talk-participants/{id}")
    public ResponseEntity<TalkParticipantDTO> getTalkParticipant(@PathVariable Long id) {
        log.debug("REST request to get TalkParticipant : {}", id);
        Optional<TalkParticipantDTO> talkParticipantDTO = talkParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(talkParticipantDTO);
    }

    /**
     * {@code DELETE  /talk-participants/:id} : delete the "id" talkParticipant.
     *
     * @param id the id of the talkParticipantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/talk-participants/{id}")
    public ResponseEntity<Void> deleteTalkParticipant(@PathVariable Long id) {
        log.debug("REST request to delete TalkParticipant : {}", id);
        talkParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
