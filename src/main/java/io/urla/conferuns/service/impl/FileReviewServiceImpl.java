package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.FileReviewService;
import io.urla.conferuns.domain.FileReview;
import io.urla.conferuns.repository.FileReviewRepository;
import io.urla.conferuns.service.dto.FileReviewDTO;
import io.urla.conferuns.service.mapper.FileReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FileReview}.
 */
@Service
@Transactional
public class FileReviewServiceImpl implements FileReviewService {

    private final Logger log = LoggerFactory.getLogger(FileReviewServiceImpl.class);

    private final FileReviewRepository fileReviewRepository;

    private final FileReviewMapper fileReviewMapper;

    public FileReviewServiceImpl(FileReviewRepository fileReviewRepository, FileReviewMapper fileReviewMapper) {
        this.fileReviewRepository = fileReviewRepository;
        this.fileReviewMapper = fileReviewMapper;
    }

    /**
     * Save a fileReview.
     *
     * @param fileReviewDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FileReviewDTO save(FileReviewDTO fileReviewDTO) {
        log.debug("Request to save FileReview : {}", fileReviewDTO);
        FileReview fileReview = fileReviewMapper.toEntity(fileReviewDTO);
        fileReview = fileReviewRepository.save(fileReview);
        return fileReviewMapper.toDto(fileReview);
    }

    /**
     * Get all the fileReviews.
     *
     * @return the list of entities.
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
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileReview : {}", id);
        fileReviewRepository.deleteById(id);
    }
}
