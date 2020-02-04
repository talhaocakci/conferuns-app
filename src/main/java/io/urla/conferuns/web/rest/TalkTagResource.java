package io.urla.conferuns.web.rest;

import io.urla.conferuns.service.TalkTagService;
import io.urla.conferuns.web.rest.errors.BadRequestAlertException;
import io.urla.conferuns.service.dto.TalkTagDTO;

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
 * REST controller for managing {@link io.urla.conferuns.domain.TalkTag}.
 */
@RestController
@RequestMapping("/api")
public class TalkTagResource {

    private final Logger log = LoggerFactory.getLogger(TalkTagResource.class);

    private static final String ENTITY_NAME = "talkTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TalkTagService talkTagService;

    public TalkTagResource(TalkTagService talkTagService) {
        this.talkTagService = talkTagService;
    }

    /**
     * {@code POST  /talk-tags} : Create a new talkTag.
     *
     * @param talkTagDTO the talkTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new talkTagDTO, or with status {@code 400 (Bad Request)} if the talkTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/talk-tags")
    public ResponseEntity<TalkTagDTO> createTalkTag(@RequestBody TalkTagDTO talkTagDTO) throws URISyntaxException {
        log.debug("REST request to save TalkTag : {}", talkTagDTO);
        if (talkTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new talkTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TalkTagDTO result = talkTagService.save(talkTagDTO);
        return ResponseEntity.created(new URI("/api/talk-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /talk-tags} : Updates an existing talkTag.
     *
     * @param talkTagDTO the talkTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated talkTagDTO,
     * or with status {@code 400 (Bad Request)} if the talkTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the talkTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/talk-tags")
    public ResponseEntity<TalkTagDTO> updateTalkTag(@RequestBody TalkTagDTO talkTagDTO) throws URISyntaxException {
        log.debug("REST request to update TalkTag : {}", talkTagDTO);
        if (talkTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TalkTagDTO result = talkTagService.save(talkTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, talkTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /talk-tags} : get all the talkTags.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of talkTags in body.
     */
    @GetMapping("/talk-tags")
    public List<TalkTagDTO> getAllTalkTags(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all TalkTags");
        return talkTagService.findAll();
    }

    /**
     * {@code GET  /talk-tags/:id} : get the "id" talkTag.
     *
     * @param id the id of the talkTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the talkTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/talk-tags/{id}")
    public ResponseEntity<TalkTagDTO> getTalkTag(@PathVariable Long id) {
        log.debug("REST request to get TalkTag : {}", id);
        Optional<TalkTagDTO> talkTagDTO = talkTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(talkTagDTO);
    }

    /**
     * {@code DELETE  /talk-tags/:id} : delete the "id" talkTag.
     *
     * @param id the id of the talkTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/talk-tags/{id}")
    public ResponseEntity<Void> deleteTalkTag(@PathVariable Long id) {
        log.debug("REST request to delete TalkTag : {}", id);
        talkTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
