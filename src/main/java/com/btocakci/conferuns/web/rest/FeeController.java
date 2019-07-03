package com.btocakci.conferuns.web.rest;
import com.btocakci.conferuns.service.FeeService;
import com.btocakci.conferuns.web.rest.errors.BadRequestAlertException;
import com.btocakci.conferuns.web.rest.util.HeaderUtil;
import com.btocakci.conferuns.service.dto.FeeDTO;
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
 * REST controller for managing Fee.
 */
@RestController
@RequestMapping("/api")
public class FeeController {

    private final Logger log = LoggerFactory.getLogger(FeeController.class);

    private static final String ENTITY_NAME = "conferunsFee";

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    /**
     * POST  /fees : Create a new fee.
     *
     * @param feeDTO the feeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feeDTO, or with status 400 (Bad Request) if the fee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fees")
    public ResponseEntity<FeeDTO> createFee(@RequestBody FeeDTO feeDTO) throws URISyntaxException {
        log.debug("REST request to save Fee : {}", feeDTO);
        if (feeDTO.getId() != null) {
            throw new BadRequestAlertException("A new fee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeeDTO result = feeService.save(feeDTO);
        return ResponseEntity.created(new URI("/api/fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fees : Updates an existing fee.
     *
     * @param feeDTO the feeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feeDTO,
     * or with status 400 (Bad Request) if the feeDTO is not valid,
     * or with status 500 (Internal Server Error) if the feeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fees")
    public ResponseEntity<FeeDTO> updateFee(@RequestBody FeeDTO feeDTO) throws URISyntaxException {
        log.debug("REST request to update Fee : {}", feeDTO);
        if (feeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeeDTO result = feeService.save(feeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fees : get all the fees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fees in body
     */
    @GetMapping("/fees")
    public List<FeeDTO> getAllFees() {
        log.debug("REST request to get all Fees");
        return feeService.findAll();
    }

    /**
     * GET  /fees/:id : get the "id" fee.
     *
     * @param id the id of the feeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fees/{id}")
    public ResponseEntity<FeeDTO> getFee(@PathVariable Long id) {
        log.debug("REST request to get Fee : {}", id);
        Optional<FeeDTO> feeDTO = feeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feeDTO);
    }

    /**
     * DELETE  /fees/:id : delete the "id" fee.
     *
     * @param id the id of the feeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fees/{id}")
    public ResponseEntity<Void> deleteFee(@PathVariable Long id) {
        log.debug("REST request to delete Fee : {}", id);
        feeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fees?query=:query : search for the fee corresponding
     * to the query.
     *
     * @param query the query of the fee search
     * @return the result of the search
     */
    @GetMapping("/_search/fees")
    public List<FeeDTO> searchFees(@RequestParam String query) {
        log.debug("REST request to search Fees for query {}", query);
        return feeService.search(query);
    }

}
