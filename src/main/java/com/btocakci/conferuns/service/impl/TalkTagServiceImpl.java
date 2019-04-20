package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.TalkTagService;
import com.btocakci.conferuns.domain.TalkTag;
import com.btocakci.conferuns.repository.TalkTagRepository;
import com.btocakci.conferuns.repository.search.TalkTagSearchRepository;
import com.btocakci.conferuns.service.dto.TalkTagDTO;
import com.btocakci.conferuns.service.mapper.TalkTagMapper;
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
 * Service Implementation for managing TalkTag.
 */
@Service
@Transactional
public class TalkTagServiceImpl implements TalkTagService {

    private final Logger log = LoggerFactory.getLogger(TalkTagServiceImpl.class);

    private final TalkTagRepository talkTagRepository;

    private final TalkTagMapper talkTagMapper;

    private final TalkTagSearchRepository talkTagSearchRepository;

    public TalkTagServiceImpl(TalkTagRepository talkTagRepository, TalkTagMapper talkTagMapper, TalkTagSearchRepository talkTagSearchRepository) {
        this.talkTagRepository = talkTagRepository;
        this.talkTagMapper = talkTagMapper;
        this.talkTagSearchRepository = talkTagSearchRepository;
    }

    /**
     * Save a talkTag.
     *
     * @param talkTagDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TalkTagDTO save(TalkTagDTO talkTagDTO) {
        log.debug("Request to save TalkTag : {}", talkTagDTO);
        TalkTag talkTag = talkTagMapper.toEntity(talkTagDTO);
        talkTag = talkTagRepository.save(talkTag);
        TalkTagDTO result = talkTagMapper.toDto(talkTag);
        talkTagSearchRepository.save(talkTag);
        return result;
    }

    /**
     * Get all the talkTags.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TalkTagDTO> findAll() {
        log.debug("Request to get all TalkTags");
        return talkTagRepository.findAllWithEagerRelationships().stream()
            .map(talkTagMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the TalkTag with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TalkTagDTO> findAllWithEagerRelationships(Pageable pageable) {
        return talkTagRepository.findAllWithEagerRelationships(pageable).map(talkTagMapper::toDto);
    }
    

    /**
     * Get one talkTag by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TalkTagDTO> findOne(Long id) {
        log.debug("Request to get TalkTag : {}", id);
        return talkTagRepository.findOneWithEagerRelationships(id)
            .map(talkTagMapper::toDto);
    }

    /**
     * Delete the talkTag by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TalkTag : {}", id);
        talkTagRepository.deleteById(id);
        talkTagSearchRepository.deleteById(id);
    }

    /**
     * Search for the talkTag corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TalkTagDTO> search(String query) {
        log.debug("Request to search TalkTags for query {}", query);
        return StreamSupport
            .stream(talkTagSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(talkTagMapper::toDto)
            .collect(Collectors.toList());
    }
}
