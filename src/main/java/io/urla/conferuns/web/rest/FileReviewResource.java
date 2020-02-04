package io.urla.conferuns.web.rest;

import io.urla.conferuns.service.FileReviewService;
import io.urla.conferuns.web.rest.errors.BadRequestAlertException;
import io.urla.conferuns.service.dto.FileReviewDTO;

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
 * REST controller for managing {@link io.urla.conferuns.domain.FileReview}.
 */
@RestController
@RequestMapping("/api")
public class FileReviewResource {

    private final Logger log = LoggerFactory.getLogger(FileReviewResource.class);

    private static final String ENTITY_NAME = "fileReview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileReviewService fileReviewService;

    public FileReviewResource(FileReviewService fileReviewService) {
        this.fileReviewService = fileReviewService;
    }

    /**
     * {@code POST  /file-reviews} : Create a new fileReview.
     *
     * @param fileReviewDTO the fileReviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileReviewDTO, or with status {@code 400 (Bad Request)} if the fileReview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-reviews")
    public ResponseEntity<FileReviewDTO> createFileReview(@RequestBody FileReviewDTO fileReviewDTO) throws URISyntaxException {
        log.debug("REST request to save FileReview : {}", fileReviewDTO);
        if (fileReviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileReviewDTO result = fileReviewService.save(fileReviewDTO);
        return ResponseEntity.created(new URI("/api/file-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-reviews} : Updates an existing fileReview.
     *
     * @param fileReviewDTO the fileReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileReviewDTO,
     * or with status {@code 400 (Bad Request)} if the fileReviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-reviews")
    public ResponseEntity<FileReviewDTO> updateFileReview(@RequestBody FileReviewDTO fileReviewDTO) throws URISyntaxException {
        log.debug("REST request to update FileReview : {}", fileReviewDTO);
        if (fileReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileReviewDTO result = fileReviewService.save(fileReviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileReviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /file-reviews} : get all the fileReviews.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileReviews in body.
     */
    @GetMapping("/file-reviews")
    public List<FileReviewDTO> getAllFileReviews() {
        log.debug("REST request to get all FileReviews");
        return fileReviewService.findAll();
    }

    /**
     * {@code GET  /file-reviews/:id} : get the "id" fileReview.
     *
     * @param id the id of the fileReviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileReviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-reviews/{id}")
    public ResponseEntity<FileReviewDTO> getFileReview(@PathVariable Long id) {
        log.debug("REST request to get FileReview : {}", id);
        Optional<FileReviewDTO> fileReviewDTO = fileReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileReviewDTO);
    }

    /**
     * {@code DELETE  /file-reviews/:id} : delete the "id" fileReview.
     *
     * @param id the id of the fileReviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-reviews/{id}")
    public ResponseEntity<Void> deleteFileReview(@PathVariable Long id) {
        log.debug("REST request to delete FileReview : {}", id);
        fileReviewService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
