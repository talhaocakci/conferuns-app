package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.ConferenceService;
import io.urla.conferuns.domain.Conference;
import io.urla.conferuns.repository.ConferenceRepository;
import io.urla.conferuns.service.dto.ConferenceDTO;
import io.urla.conferuns.service.mapper.ConferenceMapper;
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
 * Service Implementation for managing {@link Conference}.
 */
@Service
@Transactional
public class ConferenceServiceImpl implements ConferenceService {

    private final Logger log = LoggerFactory.getLogger(ConferenceServiceImpl.class);

    private final ConferenceRepository conferenceRepository;

    private final ConferenceMapper conferenceMapper;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, ConferenceMapper conferenceMapper) {
        this.conferenceRepository = conferenceRepository;
        this.conferenceMapper = conferenceMapper;
    }

    /**
     * Save a conference.
     *
     * @param conferenceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConferenceDTO save(ConferenceDTO conferenceDTO) {
        log.debug("Request to save Conference : {}", conferenceDTO);
        Conference conference = conferenceMapper.toEntity(conferenceDTO);
        conference = conferenceRepository.save(conference);
        return conferenceMapper.toDto(conference);
    }

    /**
     * Get all the conferences.
     *
     * @return the list of entities.
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
     * Get all the conferences with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ConferenceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return conferenceRepository.findAllWithEagerRelationships(pageable).map(conferenceMapper::toDto);
    }
    

    /**
     * Get one conference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conference : {}", id);
        conferenceRepository.deleteById(id);
    }
}
