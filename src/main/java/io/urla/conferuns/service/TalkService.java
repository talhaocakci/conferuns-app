package io.urla.conferuns.service;

import io.urla.conferuns.service.dto.TalkDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.urla.conferuns.domain.Talk}.
 */
public interface TalkService {

    /**
     * Save a talk.
     *
     * @param talkDTO the entity to save.
     * @return the persisted entity.
     */
    TalkDTO save(TalkDTO talkDTO);

    /**
     * Get all the talks.
     *
     * @return the list of entities.
     */
    List<TalkDTO> findAll();


    /**
     * Get the "id" talk.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TalkDTO> findOne(Long id);

    /**
     * Delete the "id" talk.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
