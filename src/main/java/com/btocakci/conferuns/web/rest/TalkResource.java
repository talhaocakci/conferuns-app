package com.btocakci.conferuns.web.rest;
import com.btocakci.conferuns.service.TalkService;
import com.btocakci.conferuns.web.rest.errors.BadRequestAlertException;
import com.btocakci.conferuns.web.rest.util.HeaderUtil;
import com.btocakci.conferuns.service.dto.TalkDTO;
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
 * REST controller for managing Talk.
 */
@RestController
@RequestMapping("/api")
public class TalkResource {

    private final Logger log = LoggerFactory.getLogger(TalkResource.class);

    private static final String ENTITY_NAME = "conferunsTalk";

    private final TalkService talkService;

    public TalkResource(TalkService talkService) {
        this.talkService = talkService;
    }

    /**
     * POST  /talks : Create a new talk.
     *
     * @param talkDTO the talkDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new talkDTO, or with status 400 (Bad Request) if the talk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/talks")
    public ResponseEntity<TalkDTO> createTalk(@RequestBody TalkDTO talkDTO) throws URISyntaxException {
        log.debug("REST request to save Talk : {}", talkDTO);
        if (talkDTO.getId() != null) {
            throw new BadRequestAlertException("A new talk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TalkDTO result = talkService.save(talkDTO);
        return ResponseEntity.created(new URI("/api/talks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /talks : Updates an existing talk.
     *
     * @param talkDTO the talkDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated talkDTO,
     * or with status 400 (Bad Request) if the talkDTO is not valid,
     * or with status 500 (Internal Server Error) if the talkDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/talks")
    public ResponseEntity<TalkDTO> updateTalk(@RequestBody TalkDTO talkDTO) throws URISyntaxException {
        log.debug("REST request to update Talk : {}", talkDTO);
        if (talkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TalkDTO result = talkService.save(talkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, talkDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /talks : get all the talks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of talks in body
     */
    @GetMapping("/talks")
    public List<TalkDTO> getAllTalks() {
        log.debug("REST request to get all Talks");
        return talkService.findAll();
    }

    /**
     * GET  /talks/:id : get the "id" talk.
     *
     * @param id the id of the talkDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the talkDTO, or with status 404 (Not Found)
     */
    @GetMapping("/talks/{id}")
    public ResponseEntity<TalkDTO> getTalk(@PathVariable Long id) {
        log.debug("REST request to get Talk : {}", id);
        Optional<TalkDTO> talkDTO = talkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(talkDTO);
    }

    /**
     * DELETE  /talks/:id : delete the "id" talk.
     *
     * @param id the id of the talkDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/talks/{id}")
    public ResponseEntity<Void> deleteTalk(@PathVariable Long id) {
        log.debug("REST request to delete Talk : {}", id);
        talkService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/talks?query=:query : search for the talk corresponding
     * to the query.
     *
     * @param query the query of the talk search
     * @return the result of the search
     */
    @GetMapping("/_search/talks")
    public List<TalkDTO> searchTalks(@RequestParam String query) {
        log.debug("REST request to search Talks for query {}", query);
        return talkService.search(query);
    }

}
