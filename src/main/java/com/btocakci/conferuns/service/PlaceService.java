package com.btocakci.conferuns.service;

import com.btocakci.conferuns.service.dto.PlaceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Place.
 */
public interface PlaceService {

    /**
     * Save a place.
     *
     * @param placeDTO the entity to save
     * @return the persisted entity
     */
    PlaceDTO save(PlaceDTO placeDTO);

    /**
     * Get all the places.
     *
     * @return the list of entities
     */
    List<PlaceDTO> findAll();


    /**
     * Get the "id" place.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PlaceDTO> findOne(Long id);

    /**
     * Delete the "id" place.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the place corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PlaceDTO> search(String query);
}
