package com.btocakci.conferuns.service;

import com.btocakci.conferuns.service.dto.FeeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Fee.
 */
public interface FeeService {

    /**
     * Save a fee.
     *
     * @param feeDTO the entity to save
     * @return the persisted entity
     */
    FeeDTO save(FeeDTO feeDTO);

    /**
     * Get all the fees.
     *
     * @return the list of entities
     */
    List<FeeDTO> findAll();


    /**
     * Get the "id" fee.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FeeDTO> findOne(Long id);

    /**
     * Delete the "id" fee.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the fee corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<FeeDTO> search(String query);
}
