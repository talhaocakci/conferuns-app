package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.ScheduleItemService;
import com.btocakci.conferuns.domain.ScheduleItem;
import com.btocakci.conferuns.repository.ScheduleItemRepository;
import com.btocakci.conferuns.repository.search.ScheduleItemSearchRepository;
import com.btocakci.conferuns.service.dto.ScheduleItemDTO;
import com.btocakci.conferuns.service.mapper.ScheduleItemMapper;
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
 * Service Implementation for managing ScheduleItem.
 */
@Service
@Transactional
public class ScheduleItemServiceImpl implements ScheduleItemService {

    private final Logger log = LoggerFactory.getLogger(ScheduleItemServiceImpl.class);

    private final ScheduleItemRepository scheduleItemRepository;

    private final ScheduleItemMapper scheduleItemMapper;

    private final ScheduleItemSearchRepository scheduleItemSearchRepository;

    public ScheduleItemServiceImpl(ScheduleItemRepository scheduleItemRepository, ScheduleItemMapper scheduleItemMapper, ScheduleItemSearchRepository scheduleItemSearchRepository) {
        this.scheduleItemRepository = scheduleItemRepository;
        this.scheduleItemMapper = scheduleItemMapper;
        this.scheduleItemSearchRepository = scheduleItemSearchRepository;
    }

    /**
     * Save a scheduleItem.
     *
     * @param scheduleItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScheduleItemDTO save(ScheduleItemDTO scheduleItemDTO) {
        log.debug("Request to save ScheduleItem : {}", scheduleItemDTO);
        ScheduleItem scheduleItem = scheduleItemMapper.toEntity(scheduleItemDTO);
        scheduleItem = scheduleItemRepository.save(scheduleItem);
        ScheduleItemDTO result = scheduleItemMapper.toDto(scheduleItem);
        scheduleItemSearchRepository.save(scheduleItem);
        return result;
    }

    /**
     * Get all the scheduleItems.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleItemDTO> findAll() {
        log.debug("Request to get all ScheduleItems");
        return scheduleItemRepository.findAll().stream()
            .map(scheduleItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one scheduleItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleItemDTO> findOne(Long id) {
        log.debug("Request to get ScheduleItem : {}", id);
        return scheduleItemRepository.findById(id)
            .map(scheduleItemMapper::toDto);
    }

    /**
     * Delete the scheduleItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduleItem : {}", id);
        scheduleItemRepository.deleteById(id);
        scheduleItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the scheduleItem corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleItemDTO> search(String query) {
        log.debug("Request to search ScheduleItems for query {}", query);
        return StreamSupport
            .stream(scheduleItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(scheduleItemMapper::toDto)
            .collect(Collectors.toList());
    }
}
