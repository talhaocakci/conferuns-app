package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.TalkHistoryService;
import com.btocakci.conferuns.domain.TalkHistory;
import com.btocakci.conferuns.repository.TalkHistoryRepository;
import com.btocakci.conferuns.repository.search.TalkHistorySearchRepository;
import com.btocakci.conferuns.service.dto.TalkHistoryDTO;
import com.btocakci.conferuns.service.mapper.TalkHistoryMapper;
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
 * Service Implementation for managing TalkHistory.
 */
@Service
@Transactional
public class TalkHistoryServiceImpl implements TalkHistoryService {

    private final Logger log = LoggerFactory.getLogger(TalkHistoryServiceImpl.class);

    private final TalkHistoryRepository talkHistoryRepository;

    private final TalkHistoryMapper talkHistoryMapper;

    private final TalkHistorySearchRepository talkHistorySearchRepository;

    public TalkHistoryServiceImpl(TalkHistoryRepository talkHistoryRepository, TalkHistoryMapper talkHistoryMapper, TalkHistorySearchRepository talkHistorySearchRepository) {
        this.talkHistoryRepository = talkHistoryRepository;
        this.talkHistoryMapper = talkHistoryMapper;
        this.talkHistorySearchRepository = talkHistorySearchRepository;
    }

    /**
     * Save a talkHistory.
     *
     * @param talkHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TalkHistoryDTO save(TalkHistoryDTO talkHistoryDTO) {
        log.debug("Request to save TalkHistory : {}", talkHistoryDTO);
        TalkHistory talkHistory = talkHistoryMapper.toEntity(talkHistoryDTO);
        talkHistory = talkHistoryRepository.save(talkHistory);
        TalkHistoryDTO result = talkHistoryMapper.toDto(talkHistory);
        talkHistorySearchRepository.save(talkHistory);
        return result;
    }

    /**
     * Get all the talkHistories.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TalkHistoryDTO> findAll() {
        log.debug("Request to get all TalkHistories");
        return talkHistoryRepository.findAll().stream()
            .map(talkHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one talkHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TalkHistoryDTO> findOne(Long id) {
        log.debug("Request to get TalkHistory : {}", id);
        return talkHistoryRepository.findById(id)
            .map(talkHistoryMapper::toDto);
    }

    /**
     * Delete the talkHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TalkHistory : {}", id);
        talkHistoryRepository.deleteById(id);
        talkHistorySearchRepository.deleteById(id);
    }

    /**
     * Search for the talkHistory corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TalkHistoryDTO> search(String query) {
        log.debug("Request to search TalkHistories for query {}", query);
        return StreamSupport
            .stream(talkHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(talkHistoryMapper::toDto)
            .collect(Collectors.toList());
    }
}
