package com.btocakci.conferuns.web.rest;
import com.btocakci.conferuns.service.PlaceService;
import com.btocakci.conferuns.web.rest.errors.BadRequestAlertException;
import com.btocakci.conferuns.web.rest.util.HeaderUtil;
import com.btocakci.conferuns.service.dto.PlaceDTO;
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
 * REST controller for managing Place.
 */
@RestController
@RequestMapping("/api")
public class PlaceResource {

    private final Logger log = LoggerFactory.getLogger(PlaceResource.class);

    private static final String ENTITY_NAME = "conferunsPlace";

    private final PlaceService placeService;

    public PlaceResource(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * POST  /places : Create a new place.
     *
     * @param placeDTO the placeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new placeDTO, or with status 400 (Bad Request) if the place has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/places")
    public ResponseEntity<PlaceDTO> createPlace(@RequestBody PlaceDTO placeDTO) throws URISyntaxException {
        log.debug("REST request to save Place : {}", placeDTO);
        if (placeDTO.getId() != null) {
            throw new BadRequestAlertException("A new place cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaceDTO result = placeService.save(placeDTO);
        return ResponseEntity.created(new URI("/api/places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /places : Updates an existing place.
     *
     * @param placeDTO the placeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated placeDTO,
     * or with status 400 (Bad Request) if the placeDTO is not valid,
     * or with status 500 (Internal Server Error) if the placeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/places")
    public ResponseEntity<PlaceDTO> updatePlace(@RequestBody PlaceDTO placeDTO) throws URISyntaxException {
        log.debug("REST request to update Place : {}", placeDTO);
        if (placeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlaceDTO result = placeService.save(placeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, placeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /places : get all the places.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of places in body
     */
    @GetMapping("/places")
    public List<PlaceDTO> getAllPlaces() {
        log.debug("REST request to get all Places");
        return placeService.findAll();
    }

    /**
     * GET  /places/:id : get the "id" place.
     *
     * @param id the id of the placeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the placeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/places/{id}")
    public ResponseEntity<PlaceDTO> getPlace(@PathVariable Long id) {
        log.debug("REST request to get Place : {}", id);
        Optional<PlaceDTO> placeDTO = placeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(placeDTO);
    }

    /**
     * DELETE  /places/:id : delete the "id" place.
     *
     * @param id the id of the placeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/places/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        log.debug("REST request to delete Place : {}", id);
        placeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/places?query=:query : search for the place corresponding
     * to the query.
     *
     * @param query the query of the place search
     * @return the result of the search
     */
    @GetMapping("/_search/places")
    public List<PlaceDTO> searchPlaces(@RequestParam String query) {
        log.debug("REST request to search Places for query {}", query);
        return placeService.search(query);
    }

}
