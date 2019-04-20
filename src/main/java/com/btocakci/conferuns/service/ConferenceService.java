package com.btocakci.conferuns.service;

import com.btocakci.conferuns.service.dto.ConferenceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Conference.
 */
public interface ConferenceService {

    /**
     * Save a conference.
     *
     * @param conferenceDTO the entity to save
     * @return the persisted entity
     */
    ConferenceDTO save(ConferenceDTO conferenceDTO);

    /**
     * Get all the conferences.
     *
     * @return the list of entities
     */
    List<ConferenceDTO> findAll();

    /**
     * Get all the Conference with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ConferenceDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" conference.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ConferenceDTO> findOne(Long id);

    /**
     * Delete the "id" conference.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the conference corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ConferenceDTO> search(String query);
}
