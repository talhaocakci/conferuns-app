package com.btocakci.conferuns.service;

import com.btocakci.conferuns.service.dto.TalkDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Talk.
 */
public interface TalkService {

    /**
     * Save a talk.
     *
     * @param talkDTO the entity to save
     * @return the persisted entity
     */
    TalkDTO save(TalkDTO talkDTO);

    /**
     * Get all the talks.
     *
     * @return the list of entities
     */
    List<TalkDTO> findAll();


    /**
     * Get the "id" talk.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TalkDTO> findOne(Long id);

    /**
     * Delete the "id" talk.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the talk corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<TalkDTO> search(String query);
}
