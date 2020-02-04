package io.urla.conferuns.service;

import io.urla.conferuns.service.dto.ConferenceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.urla.conferuns.domain.Conference}.
 */
public interface ConferenceService {

    /**
     * Save a conference.
     *
     * @param conferenceDTO the entity to save.
     * @return the persisted entity.
     */
    ConferenceDTO save(ConferenceDTO conferenceDTO);

    /**
     * Get all the conferences.
     *
     * @return the list of entities.
     */
    List<ConferenceDTO> findAll();

    /**
     * Get all the conferences with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ConferenceDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" conference.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConferenceDTO> findOne(Long id);

    /**
     * Delete the "id" conference.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
