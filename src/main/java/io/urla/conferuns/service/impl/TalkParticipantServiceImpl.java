package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.TalkParticipantService;
import io.urla.conferuns.domain.TalkParticipant;
import io.urla.conferuns.repository.TalkParticipantRepository;
import io.urla.conferuns.service.dto.TalkParticipantDTO;
import io.urla.conferuns.service.mapper.TalkParticipantMapper;
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
 * Service Implementation for managing {@link TalkParticipant}.
 */
@Service
@Transactional
public class TalkParticipantServiceImpl implements TalkParticipantService {

    private final Logger log = LoggerFactory.getLogger(TalkParticipantServiceImpl.class);

    private final TalkParticipantRepository talkParticipantRepository;

    private final TalkParticipantMapper talkParticipantMapper;

    public TalkParticipantServiceImpl(TalkParticipantRepository talkParticipantRepository, TalkParticipantMapper talkParticipantMapper) {
        this.talkParticipantRepository = talkParticipantRepository;
        this.talkParticipantMapper = talkParticipantMapper;
    }

    /**
     * Save a talkParticipant.
     *
     * @param talkParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TalkParticipantDTO save(TalkParticipantDTO talkParticipantDTO) {
        log.debug("Request to save TalkParticipant : {}", talkParticipantDTO);
        TalkParticipant talkParticipant = talkParticipantMapper.toEntity(talkParticipantDTO);
        talkParticipant = talkParticipantRepository.save(talkParticipant);
        return talkParticipantMapper.toDto(talkParticipant);
    }

    /**
     * Get all the talkParticipants.
     *
     * @return the list of entities.
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
     * Get all the talkParticipants with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TalkParticipantDTO> findAllWithEagerRelationships(Pageable pageable) {
        return talkParticipantRepository.findAllWithEagerRelationships(pageable).map(talkParticipantMapper::toDto);
    }
    

    /**
     * Get one talkParticipant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TalkParticipant : {}", id);
        talkParticipantRepository.deleteById(id);
    }
}
