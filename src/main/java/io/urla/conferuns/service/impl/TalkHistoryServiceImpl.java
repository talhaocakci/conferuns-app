package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.TalkHistoryService;
import io.urla.conferuns.domain.TalkHistory;
import io.urla.conferuns.repository.TalkHistoryRepository;
import io.urla.conferuns.service.dto.TalkHistoryDTO;
import io.urla.conferuns.service.mapper.TalkHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TalkHistory}.
 */
@Service
@Transactional
public class TalkHistoryServiceImpl implements TalkHistoryService {

    private final Logger log = LoggerFactory.getLogger(TalkHistoryServiceImpl.class);

    private final TalkHistoryRepository talkHistoryRepository;

    private final TalkHistoryMapper talkHistoryMapper;

    public TalkHistoryServiceImpl(TalkHistoryRepository talkHistoryRepository, TalkHistoryMapper talkHistoryMapper) {
        this.talkHistoryRepository = talkHistoryRepository;
        this.talkHistoryMapper = talkHistoryMapper;
    }

    /**
     * Save a talkHistory.
     *
     * @param talkHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TalkHistoryDTO save(TalkHistoryDTO talkHistoryDTO) {
        log.debug("Request to save TalkHistory : {}", talkHistoryDTO);
        TalkHistory talkHistory = talkHistoryMapper.toEntity(talkHistoryDTO);
        talkHistory = talkHistoryRepository.save(talkHistory);
        return talkHistoryMapper.toDto(talkHistory);
    }

    /**
     * Get all the talkHistories.
     *
     * @return the list of entities.
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
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TalkHistory : {}", id);
        talkHistoryRepository.deleteById(id);
    }
}
