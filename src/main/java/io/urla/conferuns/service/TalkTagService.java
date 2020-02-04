package io.urla.conferuns.service;

import io.urla.conferuns.service.dto.TalkTagDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.urla.conferuns.domain.TalkTag}.
 */
public interface TalkTagService {

    /**
     * Save a talkTag.
     *
     * @param talkTagDTO the entity to save.
     * @return the persisted entity.
     */
    TalkTagDTO save(TalkTagDTO talkTagDTO);

    /**
     * Get all the talkTags.
     *
     * @return the list of entities.
     */
    List<TalkTagDTO> findAll();

    /**
     * Get all the talkTags with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TalkTagDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" talkTag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TalkTagDTO> findOne(Long id);

    /**
     * Delete the "id" talkTag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
