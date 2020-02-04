package io.urla.conferuns.service;

import io.urla.conferuns.service.dto.ScheduleItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.urla.conferuns.domain.ScheduleItem}.
 */
public interface ScheduleItemService {

    /**
     * Save a scheduleItem.
     *
     * @param scheduleItemDTO the entity to save.
     * @return the persisted entity.
     */
    ScheduleItemDTO save(ScheduleItemDTO scheduleItemDTO);

    /**
     * Get all the scheduleItems.
     *
     * @return the list of entities.
     */
    List<ScheduleItemDTO> findAll();


    /**
     * Get the "id" scheduleItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScheduleItemDTO> findOne(Long id);

    /**
     * Delete the "id" scheduleItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
