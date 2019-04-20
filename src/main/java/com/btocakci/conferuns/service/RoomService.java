package com.btocakci.conferuns.service;

import com.btocakci.conferuns.service.dto.RoomDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Room.
 */
public interface RoomService {

    /**
     * Save a room.
     *
     * @param roomDTO the entity to save
     * @return the persisted entity
     */
    RoomDTO save(RoomDTO roomDTO);

    /**
     * Get all the rooms.
     *
     * @return the list of entities
     */
    List<RoomDTO> findAll();


    /**
     * Get the "id" room.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoomDTO> findOne(Long id);

    /**
     * Delete the "id" room.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the room corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<RoomDTO> search(String query);
}
