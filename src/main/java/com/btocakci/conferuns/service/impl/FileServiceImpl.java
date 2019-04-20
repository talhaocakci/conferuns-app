package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.FileService;
import com.btocakci.conferuns.domain.File;
import com.btocakci.conferuns.repository.FileRepository;
import com.btocakci.conferuns.repository.search.FileSearchRepository;
import com.btocakci.conferuns.service.dto.FileDTO;
import com.btocakci.conferuns.service.mapper.FileMapper;
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
 * Service Implementation for managing File.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    private final FileSearchRepository fileSearchRepository;

    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper, FileSearchRepository fileSearchRepository) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.fileSearchRepository = fileSearchRepository;
    }

    /**
     * Save a file.
     *
     * @param fileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileDTO save(FileDTO fileDTO) {
        log.debug("Request to save File : {}", fileDTO);
        File file = fileMapper.toEntity(fileDTO);
        file = fileRepository.save(file);
        FileDTO result = fileMapper.toDto(file);
        fileSearchRepository.save(file);
        return result;
    }

    /**
     * Get all the files.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileDTO> findAll() {
        log.debug("Request to get all Files");
        return fileRepository.findAll().stream()
            .map(fileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one file by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FileDTO> findOne(Long id) {
        log.debug("Request to get File : {}", id);
        return fileRepository.findById(id)
            .map(fileMapper::toDto);
    }

    /**
     * Delete the file by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.deleteById(id);
        fileSearchRepository.deleteById(id);
    }

    /**
     * Search for the file corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileDTO> search(String query) {
        log.debug("Request to search Files for query {}", query);
        return StreamSupport
            .stream(fileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(fileMapper::toDto)
            .collect(Collectors.toList());
    }
}
