package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.FileReviewService;
import com.btocakci.conferuns.domain.FileReview;
import com.btocakci.conferuns.repository.FileReviewRepository;
import com.btocakci.conferuns.repository.search.FileReviewSearchRepository;
import com.btocakci.conferuns.service.dto.FileReviewDTO;
import com.btocakci.conferuns.service.mapper.FileReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FileReview.
 */
@Service
@Transactional
public class FileReviewServiceImpl implements FileReviewService {

    private final Logger log = LoggerFactory.getLogger(FileReviewServiceImpl.class);

    private final FileReviewRepository fileReviewRepository;

    private final FileReviewMapper fileReviewMapper;

    private final FileReviewSearchRepository fileReviewSearchRepository;

    public FileReviewServiceImpl(FileReviewRepository fileReviewRepository, FileReviewMapper fileReviewMapper, FileReviewSearchRepository fileReviewSearchRepository) {
        this.fileReviewRepository = fileReviewRepository;
        this.fileReviewMapper = fileReviewMapper;
        this.fileReviewSearchRepository = fileReviewSearchRepository;
    }

    /**
     * Save a fileReview.
     *
     * @param fileReviewDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileReviewDTO save(FileReviewDTO fileReviewDTO) {
        log.debug("Request to save FileReview : {}", fileReviewDTO);
        FileReview fileReview = fileReviewMapper.toEntity(fileReviewDTO);
        fileReview = fileReviewRepository.save(fileReview);
        FileReviewDTO result = fileReviewMapper.toDto(fileReview);
        fileReviewSearchRepository.save(fileReview);
        return result;
    }

    /**
     * Get all the fileReviews.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileReviewDTO> findAll() {
        log.debug("Request to get all FileReviews");
        return fileReviewRepository.findAll().stream()
            .map(fileReviewMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one fileReview by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FileReviewDTO> findOne(Long id) {
        log.debug("Request to get FileReview : {}", id);
        return fileReviewRepository.findById(id)
            .map(fileReviewMapper::toDto);
    }

    /**
     * Delete the fileReview by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileReview : {}", id);
        fileReviewRepository.deleteById(id);
        fileReviewSearchRepository.deleteById(id);
    }

    /**
     * Search for the fileReview corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileReviewDTO> search(String query) {
        log.debug("Request to search FileReviews for query {}", query);
        return StreamSupport
            .stream(fileReviewSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(fileReviewMapper::toDto)
            .collect(Collectors.toList());
    }
}
