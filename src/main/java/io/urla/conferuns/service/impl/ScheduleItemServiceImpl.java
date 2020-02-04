package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.ScheduleItemService;
import io.urla.conferuns.domain.ScheduleItem;
import io.urla.conferuns.repository.ScheduleItemRepository;
import io.urla.conferuns.service.dto.ScheduleItemDTO;
import io.urla.conferuns.service.mapper.ScheduleItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ScheduleItem}.
 */
@Service
@Transactional
public class ScheduleItemServiceImpl implements ScheduleItemService {

    private final Logger log = LoggerFactory.getLogger(ScheduleItemServiceImpl.class);

    private final ScheduleItemRepository scheduleItemRepository;

    private final ScheduleItemMapper scheduleItemMapper;

    public ScheduleItemServiceImpl(ScheduleItemRepository scheduleItemRepository, ScheduleItemMapper scheduleItemMapper) {
        this.scheduleItemRepository = scheduleItemRepository;
        this.scheduleItemMapper = scheduleItemMapper;
    }

    /**
     * Save a scheduleItem.
     *
     * @param scheduleItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ScheduleItemDTO save(ScheduleItemDTO scheduleItemDTO) {
        log.debug("Request to save ScheduleItem : {}", scheduleItemDTO);
        ScheduleItem scheduleItem = scheduleItemMapper.toEntity(scheduleItemDTO);
        scheduleItem = scheduleItemRepository.save(scheduleItem);
        return scheduleItemMapper.toDto(scheduleItem);
    }

    /**
     * Get all the scheduleItems.
     *
     * @return the list of entities.
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
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduleItem : {}", id);
        scheduleItemRepository.deleteById(id);
    }
}
