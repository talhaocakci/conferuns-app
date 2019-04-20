package com.btocakci.conferuns.web.rest;
import com.btocakci.conferuns.service.TalkHistoryService;
import com.btocakci.conferuns.web.rest.errors.BadRequestAlertException;
import com.btocakci.conferuns.web.rest.util.HeaderUtil;
import com.btocakci.conferuns.service.dto.TalkHistoryDTO;
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
 * REST controller for managing TalkHistory.
 */
@RestController
@RequestMapping("/api")
public class TalkHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TalkHistoryResource.class);

    private static final String ENTITY_NAME = "conferunsTalkHistory";

    private final TalkHistoryService talkHistoryService;

    public TalkHistoryResource(TalkHistoryService talkHistoryService) {
        this.talkHistoryService = talkHistoryService;
    }

    /**
     * POST  /talk-histories : Create a new talkHistory.
     *
     * @param talkHistoryDTO the talkHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new talkHistoryDTO, or with status 400 (Bad Request) if the talkHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/talk-histories")
    public ResponseEntity<TalkHistoryDTO> createTalkHistory(@RequestBody TalkHistoryDTO talkHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save TalkHistory : {}", talkHistoryDTO);
        if (talkHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new talkHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TalkHistoryDTO result = talkHistoryService.save(talkHistoryDTO);
        return ResponseEntity.created(new URI("/api/talk-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /talk-histories : Updates an existing talkHistory.
     *
     * @param talkHistoryDTO the talkHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated talkHistoryDTO,
     * or with status 400 (Bad Request) if the talkHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the talkHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/talk-histories")
    public ResponseEntity<TalkHistoryDTO> updateTalkHistory(@RequestBody TalkHistoryDTO talkHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update TalkHistory : {}", talkHistoryDTO);
        if (talkHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TalkHistoryDTO result = talkHistoryService.save(talkHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, talkHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /talk-histories : get all the talkHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of talkHistories in body
     */
    @GetMapping("/talk-histories")
    public List<TalkHistoryDTO> getAllTalkHistories() {
        log.debug("REST request to get all TalkHistories");
        return talkHistoryService.findAll();
    }

    /**
     * GET  /talk-histories/:id : get the "id" talkHistory.
     *
     * @param id the id of the talkHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the talkHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/talk-histories/{id}")
    public ResponseEntity<TalkHistoryDTO> getTalkHistory(@PathVariable Long id) {
        log.debug("REST request to get TalkHistory : {}", id);
        Optional<TalkHistoryDTO> talkHistoryDTO = talkHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(talkHistoryDTO);
    }

    /**
     * DELETE  /talk-histories/:id : delete the "id" talkHistory.
     *
     * @param id the id of the talkHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/talk-histories/{id}")
    public ResponseEntity<Void> deleteTalkHistory(@PathVariable Long id) {
        log.debug("REST request to delete TalkHistory : {}", id);
        talkHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/talk-histories?query=:query : search for the talkHistory corresponding
     * to the query.
     *
     * @param query the query of the talkHistory search
     * @return the result of the search
     */
    @GetMapping("/_search/talk-histories")
    public List<TalkHistoryDTO> searchTalkHistories(@RequestParam String query) {
        log.debug("REST request to search TalkHistories for query {}", query);
        return talkHistoryService.search(query);
    }

}
