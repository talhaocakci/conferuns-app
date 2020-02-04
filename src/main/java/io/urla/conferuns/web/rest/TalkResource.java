package io.urla.conferuns.web.rest;

import io.urla.conferuns.service.TalkService;
import io.urla.conferuns.web.rest.errors.BadRequestAlertException;
import io.urla.conferuns.service.dto.TalkDTO;

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
 * REST controller for managing {@link io.urla.conferuns.domain.Talk}.
 */
@RestController
@RequestMapping("/api")
public class TalkResource {

    private final Logger log = LoggerFactory.getLogger(TalkResource.class);

    private static final String ENTITY_NAME = "talk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TalkService talkService;

    public TalkResource(TalkService talkService) {
        this.talkService = talkService;
    }

    /**
     * {@code POST  /talks} : Create a new talk.
     *
     * @param talkDTO the talkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new talkDTO, or with status {@code 400 (Bad Request)} if the talk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/talks")
    public ResponseEntity<TalkDTO> createTalk(@RequestBody TalkDTO talkDTO) throws URISyntaxException {
        log.debug("REST request to save Talk : {}", talkDTO);
        if (talkDTO.getId() != null) {
            throw new BadRequestAlertException("A new talk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TalkDTO result = talkService.save(talkDTO);
        return ResponseEntity.created(new URI("/api/talks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /talks} : Updates an existing talk.
     *
     * @param talkDTO the talkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated talkDTO,
     * or with status {@code 400 (Bad Request)} if the talkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the talkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/talks")
    public ResponseEntity<TalkDTO> updateTalk(@RequestBody TalkDTO talkDTO) throws URISyntaxException {
        log.debug("REST request to update Talk : {}", talkDTO);
        if (talkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TalkDTO result = talkService.save(talkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, talkDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /talks} : get all the talks.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of talks in body.
     */
    @GetMapping("/talks")
    public List<TalkDTO> getAllTalks() {
        log.debug("REST request to get all Talks");
        return talkService.findAll();
    }

    /**
     * {@code GET  /talks/:id} : get the "id" talk.
     *
     * @param id the id of the talkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the talkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/talks/{id}")
    public ResponseEntity<TalkDTO> getTalk(@PathVariable Long id) {
        log.debug("REST request to get Talk : {}", id);
        Optional<TalkDTO> talkDTO = talkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(talkDTO);
    }

    /**
     * {@code DELETE  /talks/:id} : delete the "id" talk.
     *
     * @param id the id of the talkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/talks/{id}")
    public ResponseEntity<Void> deleteTalk(@PathVariable Long id) {
        log.debug("REST request to delete Talk : {}", id);
        talkService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
