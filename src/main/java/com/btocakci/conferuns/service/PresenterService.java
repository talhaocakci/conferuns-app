package com.btocakci.conferuns.service;

import com.btocakci.conferuns.service.dto.PresenterDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Presenter.
 */
public interface PresenterService {

    /**
     * Save a presenter.
     *
     * @param presenterDTO the entity to save
     * @return the persisted entity
     */
    PresenterDTO save(PresenterDTO presenterDTO);

    /**
     * Get all the presenters.
     *
     * @return the list of entities
     */
    List<PresenterDTO> findAll();


    /**
     * Get the "id" presenter.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PresenterDTO> findOne(Long id);

    /**
     * Delete the "id" presenter.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the presenter corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PresenterDTO> search(String query);
}
