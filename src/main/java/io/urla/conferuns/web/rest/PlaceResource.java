package io.urla.conferuns.web.rest;

import io.urla.conferuns.service.PlaceService;
import io.urla.conferuns.web.rest.errors.BadRequestAlertException;
import io.urla.conferuns.service.dto.PlaceDTO;

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
 * REST controller for managing {@link io.urla.conferuns.domain.Place}.
 */
@RestController
@RequestMapping("/api")
public class PlaceResource {

    private final Logger log = LoggerFactory.getLogger(PlaceResource.class);

    private static final String ENTITY_NAME = "place";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaceService placeService;

    public PlaceResource(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * {@code POST  /places} : Create a new place.
     *
     * @param placeDTO the placeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new placeDTO, or with status {@code 400 (Bad Request)} if the place has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/places")
    public ResponseEntity<PlaceDTO> createPlace(@RequestBody PlaceDTO placeDTO) throws URISyntaxException {
        log.debug("REST request to save Place : {}", placeDTO);
        if (placeDTO.getId() != null) {
            throw new BadRequestAlertException("A new place cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaceDTO result = placeService.save(placeDTO);
        return ResponseEntity.created(new URI("/api/places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /places} : Updates an existing place.
     *
     * @param placeDTO the placeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated placeDTO,
     * or with status {@code 400 (Bad Request)} if the placeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the placeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/places")
    public ResponseEntity<PlaceDTO> updatePlace(@RequestBody PlaceDTO placeDTO) throws URISyntaxException {
        log.debug("REST request to update Place : {}", placeDTO);
        if (placeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlaceDTO result = placeService.save(placeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, placeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /places} : get all the places.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of places in body.
     */
    @GetMapping("/places")
    public List<PlaceDTO> getAllPlaces() {
        log.debug("REST request to get all Places");
        return placeService.findAll();
    }

    /**
     * {@code GET  /places/:id} : get the "id" place.
     *
     * @param id the id of the placeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the placeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/places/{id}")
    public ResponseEntity<PlaceDTO> getPlace(@PathVariable Long id) {
        log.debug("REST request to get Place : {}", id);
        Optional<PlaceDTO> placeDTO = placeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(placeDTO);
    }

    /**
     * {@code DELETE  /places/:id} : delete the "id" place.
     *
     * @param id the id of the placeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/places/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        log.debug("REST request to delete Place : {}", id);
        placeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
