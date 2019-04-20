package com.btocakci.conferuns.service;

import com.btocakci.conferuns.service.dto.ScheduleItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ScheduleItem.
 */
public interface ScheduleItemService {

    /**
     * Save a scheduleItem.
     *
     * @param scheduleItemDTO the entity to save
     * @return the persisted entity
     */
    ScheduleItemDTO save(ScheduleItemDTO scheduleItemDTO);

    /**
     * Get all the scheduleItems.
     *
     * @return the list of entities
     */
    List<ScheduleItemDTO> findAll();


    /**
     * Get the "id" scheduleItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ScheduleItemDTO> findOne(Long id);

    /**
     * Delete the "id" scheduleItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the scheduleItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ScheduleItemDTO> search(String query);
}
