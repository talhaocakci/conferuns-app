package com.btocakci.conferuns.web.rest;
import com.btocakci.conferuns.service.PresenterService;
import com.btocakci.conferuns.web.rest.errors.BadRequestAlertException;
import com.btocakci.conferuns.web.rest.util.HeaderUtil;
import com.btocakci.conferuns.service.dto.PresenterDTO;
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
 * REST controller for managing Presenter.
 */
@RestController
@RequestMapping("/api")
public class PresenterResource {

    private final Logger log = LoggerFactory.getLogger(PresenterResource.class);

    private static final String ENTITY_NAME = "conferunsPresenter";

    private final PresenterService presenterService;

    public PresenterResource(PresenterService presenterService) {
        this.presenterService = presenterService;
    }

    /**
     * POST  /presenters : Create a new presenter.
     *
     * @param presenterDTO the presenterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new presenterDTO, or with status 400 (Bad Request) if the presenter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/presenters")
    public ResponseEntity<PresenterDTO> createPresenter(@RequestBody PresenterDTO presenterDTO) throws URISyntaxException {
        log.debug("REST request to save Presenter : {}", presenterDTO);
        if (presenterDTO.getId() != null) {
            throw new BadRequestAlertException("A new presenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PresenterDTO result = presenterService.save(presenterDTO);
        return ResponseEntity.created(new URI("/api/presenters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /presenters : Updates an existing presenter.
     *
     * @param presenterDTO the presenterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated presenterDTO,
     * or with status 400 (Bad Request) if the presenterDTO is not valid,
     * or with status 500 (Internal Server Error) if the presenterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/presenters")
    public ResponseEntity<PresenterDTO> updatePresenter(@RequestBody PresenterDTO presenterDTO) throws URISyntaxException {
        log.debug("REST request to update Presenter : {}", presenterDTO);
        if (presenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PresenterDTO result = presenterService.save(presenterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, presenterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /presenters : get all the presenters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of presenters in body
     */
    @GetMapping("/presenters")
    public List<PresenterDTO> getAllPresenters() {
        log.debug("REST request to get all Presenters");
        return presenterService.findAll();
    }

    /**
     * GET  /presenters/:id : get the "id" presenter.
     *
     * @param id the id of the presenterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the presenterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/presenters/{id}")
    public ResponseEntity<PresenterDTO> getPresenter(@PathVariable Long id) {
        log.debug("REST request to get Presenter : {}", id);
        Optional<PresenterDTO> presenterDTO = presenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(presenterDTO);
    }

    /**
     * DELETE  /presenters/:id : delete the "id" presenter.
     *
     * @param id the id of the presenterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/presenters/{id}")
    public ResponseEntity<Void> deletePresenter(@PathVariable Long id) {
        log.debug("REST request to delete Presenter : {}", id);
        presenterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/presenters?query=:query : search for the presenter corresponding
     * to the query.
     *
     * @param query the query of the presenter search
     * @return the result of the search
     */
    @GetMapping("/_search/presenters")
    public List<PresenterDTO> searchPresenters(@RequestParam String query) {
        log.debug("REST request to search Presenters for query {}", query);
        return presenterService.search(query);
    }

}
