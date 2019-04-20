package com.btocakci.conferuns.service;

import com.btocakci.conferuns.service.dto.TalkHistoryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TalkHistory.
 */
public interface TalkHistoryService {

    /**
     * Save a talkHistory.
     *
     * @param talkHistoryDTO the entity to save
     * @return the persisted entity
     */
    TalkHistoryDTO save(TalkHistoryDTO talkHistoryDTO);

    /**
     * Get all the talkHistories.
     *
     * @return the list of entities
     */
    List<TalkHistoryDTO> findAll();


    /**
     * Get the "id" talkHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TalkHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" talkHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the talkHistory corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<TalkHistoryDTO> search(String query);
}
