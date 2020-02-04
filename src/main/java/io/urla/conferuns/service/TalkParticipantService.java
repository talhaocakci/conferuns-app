package io.urla.conferuns.service;

import io.urla.conferuns.service.dto.TalkParticipantDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.urla.conferuns.domain.TalkParticipant}.
 */
public interface TalkParticipantService {

    /**
     * Save a talkParticipant.
     *
     * @param talkParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    TalkParticipantDTO save(TalkParticipantDTO talkParticipantDTO);

    /**
     * Get all the talkParticipants.
     *
     * @return the list of entities.
     */
    List<TalkParticipantDTO> findAll();

    /**
     * Get all the talkParticipants with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TalkParticipantDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" talkParticipant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TalkParticipantDTO> findOne(Long id);

    /**
     * Delete the "id" talkParticipant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
