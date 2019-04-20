package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.ConferenceService;
import com.btocakci.conferuns.domain.Conference;
import com.btocakci.conferuns.repository.ConferenceRepository;
import com.btocakci.conferuns.repository.search.ConferenceSearchRepository;
import com.btocakci.conferuns.service.dto.ConferenceDTO;
import com.btocakci.conferuns.service.mapper.ConferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Conference.
 */
@Service
@Transactional
public class ConferenceServiceImpl implements ConferenceService {

    private final Logger log = LoggerFactory.getLogger(ConferenceServiceImpl.class);

    private final ConferenceRepository conferenceRepository;

    private final ConferenceMapper conferenceMapper;

    private final ConferenceSearchRepository conferenceSearchRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, ConferenceMapper conferenceMapper, ConferenceSearchRepository conferenceSearchRepository) {
        this.conferenceRepository = conferenceRepository;
        this.conferenceMapper = conferenceMapper;
        this.conferenceSearchRepository = conferenceSearchRepository;
    }

    /**
     * Save a conference.
     *
     * @param conferenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConferenceDTO save(ConferenceDTO conferenceDTO) {
        log.debug("Request to save Conference : {}", conferenceDTO);
        Conference conference = conferenceMapper.toEntity(conferenceDTO);
        conference = conferenceRepository.save(conference);
        ConferenceDTO result = conferenceMapper.toDto(conference);
        conferenceSearchRepository.save(conference);
        return result;
    }

    /**
     * Get all the conferences.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDTO> findAll() {
        log.debug("Request to get all Conferences");
        return conferenceRepository.findAllWithEagerRelationships().stream()
            .map(conferenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Conference with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ConferenceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return conferenceRepository.findAllWithEagerRelationships(pageable).map(conferenceMapper::toDto);
    }
    

    /**
     * Get one conference by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConferenceDTO> findOne(Long id) {
        log.debug("Request to get Conference : {}", id);
        return conferenceRepository.findOneWithEagerRelationships(id)
            .map(conferenceMapper::toDto);
    }

    /**
     * Delete the conference by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conference : {}", id);
        conferenceRepository.deleteById(id);
        conferenceSearchRepository.deleteById(id);
    }

    /**
     * Search for the conference corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDTO> search(String query) {
        log.debug("Request to search Conferences for query {}", query);
        return StreamSupport
            .stream(conferenceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(conferenceMapper::toDto)
            .collect(Collectors.toList());
    }
}
