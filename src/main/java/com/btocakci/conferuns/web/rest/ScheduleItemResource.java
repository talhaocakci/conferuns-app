package com.btocakci.conferuns.web.rest;
import com.btocakci.conferuns.service.ScheduleItemService;
import com.btocakci.conferuns.web.rest.errors.BadRequestAlertException;
import com.btocakci.conferuns.web.rest.util.HeaderUtil;
import com.btocakci.conferuns.service.dto.ScheduleItemDTO;
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
 * REST controller for managing ScheduleItem.
 */
@RestController
@RequestMapping("/api")
public class ScheduleItemResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleItemResource.class);

    private static final String ENTITY_NAME = "conferunsScheduleItem";

    private final ScheduleItemService scheduleItemService;

    public ScheduleItemResource(ScheduleItemService scheduleItemService) {
        this.scheduleItemService = scheduleItemService;
    }

    /**
     * POST  /schedule-items : Create a new scheduleItem.
     *
     * @param scheduleItemDTO the scheduleItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheduleItemDTO, or with status 400 (Bad Request) if the scheduleItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schedule-items")
    public ResponseEntity<ScheduleItemDTO> createScheduleItem(@RequestBody ScheduleItemDTO scheduleItemDTO) throws URISyntaxException {
        log.debug("REST request to save ScheduleItem : {}", scheduleItemDTO);
        if (scheduleItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheduleItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleItemDTO result = scheduleItemService.save(scheduleItemDTO);
        return ResponseEntity.created(new URI("/api/schedule-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schedule-items : Updates an existing scheduleItem.
     *
     * @param scheduleItemDTO the scheduleItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scheduleItemDTO,
     * or with status 400 (Bad Request) if the scheduleItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the scheduleItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schedule-items")
    public ResponseEntity<ScheduleItemDTO> updateScheduleItem(@RequestBody ScheduleItemDTO scheduleItemDTO) throws URISyntaxException {
        log.debug("REST request to update ScheduleItem : {}", scheduleItemDTO);
        if (scheduleItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduleItemDTO result = scheduleItemService.save(scheduleItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scheduleItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schedule-items : get all the scheduleItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of scheduleItems in body
     */
    @GetMapping("/schedule-items")
    public List<ScheduleItemDTO> getAllScheduleItems() {
        log.debug("REST request to get all ScheduleItems");
        return scheduleItemService.findAll();
    }

    /**
     * GET  /schedule-items/:id : get the "id" scheduleItem.
     *
     * @param id the id of the scheduleItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scheduleItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/schedule-items/{id}")
    public ResponseEntity<ScheduleItemDTO> getScheduleItem(@PathVariable Long id) {
        log.debug("REST request to get ScheduleItem : {}", id);
        Optional<ScheduleItemDTO> scheduleItemDTO = scheduleItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleItemDTO);
    }

    /**
     * DELETE  /schedule-items/:id : delete the "id" scheduleItem.
     *
     * @param id the id of the scheduleItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schedule-items/{id}")
    public ResponseEntity<Void> deleteScheduleItem(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleItem : {}", id);
        scheduleItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/schedule-items?query=:query : search for the scheduleItem corresponding
     * to the query.
     *
     * @param query the query of the scheduleItem search
     * @return the result of the search
     */
    @GetMapping("/_search/schedule-items")
    public List<ScheduleItemDTO> searchScheduleItems(@RequestParam String query) {
        log.debug("REST request to search ScheduleItems for query {}", query);
        return scheduleItemService.search(query);
    }

}
