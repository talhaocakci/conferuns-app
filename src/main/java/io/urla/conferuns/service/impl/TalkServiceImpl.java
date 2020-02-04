package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.TalkService;
import io.urla.conferuns.domain.Talk;
import io.urla.conferuns.repository.TalkRepository;
import io.urla.conferuns.service.dto.TalkDTO;
import io.urla.conferuns.service.mapper.TalkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Talk}.
 */
@Service
@Transactional
public class TalkServiceImpl implements TalkService {

    private final Logger log = LoggerFactory.getLogger(TalkServiceImpl.class);

    private final TalkRepository talkRepository;

    private final TalkMapper talkMapper;

    public TalkServiceImpl(TalkRepository talkRepository, TalkMapper talkMapper) {
        this.talkRepository = talkRepository;
        this.talkMapper = talkMapper;
    }

    /**
     * Save a talk.
     *
     * @param talkDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TalkDTO save(TalkDTO talkDTO) {
        log.debug("Request to save Talk : {}", talkDTO);
        Talk talk = talkMapper.toEntity(talkDTO);
        talk = talkRepository.save(talk);
        return talkMapper.toDto(talk);
    }

    /**
     * Get all the talks.
     *
     * @return the list of entities.
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
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Talk : {}", id);
        talkRepository.deleteById(id);
    }
}
