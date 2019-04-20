package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.TalkService;
import com.btocakci.conferuns.domain.Talk;
import com.btocakci.conferuns.repository.TalkRepository;
import com.btocakci.conferuns.repository.search.TalkSearchRepository;
import com.btocakci.conferuns.service.dto.TalkDTO;
import com.btocakci.conferuns.service.mapper.TalkMapper;
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
 * Service Implementation for managing Talk.
 */
@Service
@Transactional
public class TalkServiceImpl implements TalkService {

    private final Logger log = LoggerFactory.getLogger(TalkServiceImpl.class);

    private final TalkRepository talkRepository;

    private final TalkMapper talkMapper;

    private final TalkSearchRepository talkSearchRepository;

    public TalkServiceImpl(TalkRepository talkRepository, TalkMapper talkMapper, TalkSearchRepository talkSearchRepository) {
        this.talkRepository = talkRepository;
        this.talkMapper = talkMapper;
        this.talkSearchRepository = talkSearchRepository;
    }

    /**
     * Save a talk.
     *
     * @param talkDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TalkDTO save(TalkDTO talkDTO) {
        log.debug("Request to save Talk : {}", talkDTO);
        Talk talk = talkMapper.toEntity(talkDTO);
        talk = talkRepository.save(talk);
        TalkDTO result = talkMapper.toDto(talk);
        talkSearchRepository.save(talk);
        return result;
    }

    /**
     * Get all the talks.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TalkDTO> findAll() {
        log.debug("Request to get all Talks");
        return talkRepository.findAll().stream()
            .map(talkMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one talk by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TalkDTO> findOne(Long id) {
        log.debug("Request to get Talk : {}", id);
        return talkRepository.findById(id)
            .map(talkMapper::toDto);
    }

    /**
     * Delete the talk by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Talk : {}", id);
        talkRepository.deleteById(id);
        talkSearchRepository.deleteById(id);
    }

    /**
     * Search for the talk corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TalkDTO> search(String query) {
        log.debug("Request to search Talks for query {}", query);
        return StreamSupport
            .stream(talkSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(talkMapper::toDto)
            .collect(Collectors.toList());
    }
}
