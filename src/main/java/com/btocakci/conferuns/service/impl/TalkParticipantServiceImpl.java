package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.TalkParticipantService;
import com.btocakci.conferuns.domain.TalkParticipant;
import com.btocakci.conferuns.repository.TalkParticipantRepository;
import com.btocakci.conferuns.repository.search.TalkParticipantSearchRepository;
import com.btocakci.conferuns.service.dto.TalkParticipantDTO;
import com.btocakci.conferuns.service.mapper.TalkParticipantMapper;
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
 * Service Implementation for managing TalkParticipant.
 */
@Service
@Transactional
public class TalkParticipantServiceImpl implements TalkParticipantService {

    private final Logger log = LoggerFactory.getLogger(TalkParticipantServiceImpl.class);

    private final TalkParticipantRepository talkParticipantRepository;

    private final TalkParticipantMapper talkParticipantMapper;

    private final TalkParticipantSearchRepository talkParticipantSearchRepository;

    public TalkParticipantServiceImpl(TalkParticipantRepository talkParticipantRepository, TalkParticipantMapper talkParticipantMapper, TalkParticipantSearchRepository talkParticipantSearchRepository) {
        this.talkParticipantRepository = talkParticipantRepository;
        this.talkParticipantMapper = talkParticipantMapper;
        this.talkParticipantSearchRepository = talkParticipantSearchRepository;
    }

    /**
     * Save a talkParticipant.
     *
     * @param talkParticipantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TalkParticipantDTO save(TalkParticipantDTO talkParticipantDTO) {
        log.debug("Request to save TalkParticipant : {}", talkParticipantDTO);
        TalkParticipant talkParticipant = talkParticipantMapper.toEntity(talkParticipantDTO);
        talkParticipant = talkParticipantRepository.save(talkParticipant);
        TalkParticipantDTO result = talkParticipantMapper.toDto(talkParticipant);
        talkParticipantSearchRepository.save(talkParticipant);
        return result;
    }

    /**
     * Get all the talkParticipants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TalkParticipantDTO> findAll() {
        log.debug("Request to get all TalkParticipants");
        return talkParticipantRepository.findAllWithEagerRelationships().stream()
            .map(talkParticipantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the TalkParticipant with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TalkParticipantDTO> findAllWithEagerRelationships(Pageable pageable) {
        return talkParticipantRepository.findAllWithEagerRelationships(pageable).map(talkParticipantMapper::toDto);
    }
    

    /**
     * Get one talkParticipant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TalkParticipantDTO> findOne(Long id) {
        log.debug("Request to get TalkParticipant : {}", id);
        return talkParticipantRepository.findOneWithEagerRelationships(id)
            .map(talkParticipantMapper::toDto);
    }

    /**
     * Delete the talkParticipant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TalkParticipant : {}", id);
        talkParticipantRepository.deleteById(id);
        talkParticipantSearchRepository.deleteById(id);
    }

    /**
     * Search for the talkParticipant corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TalkParticipantDTO> search(String query) {
        log.debug("Request to search TalkParticipants for query {}", query);
        return StreamSupport
            .stream(talkParticipantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(talkParticipantMapper::toDto)
            .collect(Collectors.toList());
    }
}
