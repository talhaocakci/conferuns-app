package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.TalkTagService;
import io.urla.conferuns.domain.TalkTag;
import io.urla.conferuns.repository.TalkTagRepository;
import io.urla.conferuns.service.dto.TalkTagDTO;
import io.urla.conferuns.service.mapper.TalkTagMapper;
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

/**
 * Service Implementation for managing {@link TalkTag}.
 */
@Service
@Transactional
public class TalkTagServiceImpl implements TalkTagService {

    private final Logger log = LoggerFactory.getLogger(TalkTagServiceImpl.class);

    private final TalkTagRepository talkTagRepository;

    private final TalkTagMapper talkTagMapper;

    public TalkTagServiceImpl(TalkTagRepository talkTagRepository, TalkTagMapper talkTagMapper) {
        this.talkTagRepository = talkTagRepository;
        this.talkTagMapper = talkTagMapper;
    }

    /**
     * Save a talkTag.
     *
     * @param talkTagDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TalkTagDTO save(TalkTagDTO talkTagDTO) {
        log.debug("Request to save TalkTag : {}", talkTagDTO);
        TalkTag talkTag = talkTagMapper.toEntity(talkTagDTO);
        talkTag = talkTagRepository.save(talkTag);
        return talkTagMapper.toDto(talkTag);
    }

    /**
     * Get all the talkTags.
     *
     * @return the list of entities.
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
     * Get all the talkTags with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TalkTagDTO> findAllWithEagerRelationships(Pageable pageable) {
        return talkTagRepository.findAllWithEagerRelationships(pageable).map(talkTagMapper::toDto);
    }
    

    /**
     * Get one talkTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TalkTag : {}", id);
        talkTagRepository.deleteById(id);
    }
}
