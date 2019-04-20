package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.PresenterService;
import com.btocakci.conferuns.domain.Presenter;
import com.btocakci.conferuns.repository.PresenterRepository;
import com.btocakci.conferuns.repository.search.PresenterSearchRepository;
import com.btocakci.conferuns.service.dto.PresenterDTO;
import com.btocakci.conferuns.service.mapper.PresenterMapper;
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
 * Service Implementation for managing Presenter.
 */
@Service
@Transactional
public class PresenterServiceImpl implements PresenterService {

    private final Logger log = LoggerFactory.getLogger(PresenterServiceImpl.class);

    private final PresenterRepository presenterRepository;

    private final PresenterMapper presenterMapper;

    private final PresenterSearchRepository presenterSearchRepository;

    public PresenterServiceImpl(PresenterRepository presenterRepository, PresenterMapper presenterMapper, PresenterSearchRepository presenterSearchRepository) {
        this.presenterRepository = presenterRepository;
        this.presenterMapper = presenterMapper;
        this.presenterSearchRepository = presenterSearchRepository;
    }

    /**
     * Save a presenter.
     *
     * @param presenterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PresenterDTO save(PresenterDTO presenterDTO) {
        log.debug("Request to save Presenter : {}", presenterDTO);
        Presenter presenter = presenterMapper.toEntity(presenterDTO);
        presenter = presenterRepository.save(presenter);
        PresenterDTO result = presenterMapper.toDto(presenter);
        presenterSearchRepository.save(presenter);
        return result;
    }

    /**
     * Get all the presenters.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PresenterDTO> findAll() {
        log.debug("Request to get all Presenters");
        return presenterRepository.findAll().stream()
            .map(presenterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one presenter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PresenterDTO> findOne(Long id) {
        log.debug("Request to get Presenter : {}", id);
        return presenterRepository.findById(id)
            .map(presenterMapper::toDto);
    }

    /**
     * Delete the presenter by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Presenter : {}", id);
        presenterRepository.deleteById(id);
        presenterSearchRepository.deleteById(id);
    }

    /**
     * Search for the presenter corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PresenterDTO> search(String query) {
        log.debug("Request to search Presenters for query {}", query);
        return StreamSupport
            .stream(presenterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(presenterMapper::toDto)
            .collect(Collectors.toList());
    }
}
