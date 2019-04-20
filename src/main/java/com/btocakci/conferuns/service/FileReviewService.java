package com.btocakci.conferuns.service;

import com.btocakci.conferuns.service.dto.FileReviewDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing FileReview.
 */
public interface FileReviewService {

    /**
     * Save a fileReview.
     *
     * @param fileReviewDTO the entity to save
     * @return the persisted entity
     */
    FileReviewDTO save(FileReviewDTO fileReviewDTO);

    /**
     * Get all the fileReviews.
     *
     * @return the list of entities
     */
    List<FileReviewDTO> findAll();


    /**
     * Get the "id" fileReview.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FileReviewDTO> findOne(Long id);

    /**
     * Delete the "id" fileReview.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the fileReview corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<FileReviewDTO> search(String query);
}
