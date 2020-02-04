package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.PresenterService;
import io.urla.conferuns.domain.Presenter;
import io.urla.conferuns.repository.PresenterRepository;
import io.urla.conferuns.service.dto.PresenterDTO;
import io.urla.conferuns.service.mapper.PresenterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Presenter}.
 */
@Service
@Transactional
public class PresenterServiceImpl implements PresenterService {

    private final Logger log = LoggerFactory.getLogger(PresenterServiceImpl.class);

    private final PresenterRepository presenterRepository;

    private final PresenterMapper presenterMapper;

    public PresenterServiceImpl(PresenterRepository presenterRepository, PresenterMapper presenterMapper) {
        this.presenterRepository = presenterRepository;
        this.presenterMapper = presenterMapper;
    }

    /**
     * Save a presenter.
     *
     * @param presenterDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PresenterDTO save(PresenterDTO presenterDTO) {
        log.debug("Request to save Presenter : {}", presenterDTO);
        Presenter presenter = presenterMapper.toEntity(presenterDTO);
        presenter = presenterRepository.save(presenter);
        return presenterMapper.toDto(presenter);
    }

    /**
     * Get all the presenters.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PresenterDTO> findAll() {
        log.debug("Request to get all Presenters");
        return presenterRepository.findAll().stream()
            .map(presenterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one presenter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PresenterDTO> findOne(Long id) {
        log.debug("Request to get Presenter : {}", id);
        return presenterRepository.findById(id)
            .map(presenterMapper::toDto);
    }

    /**
     * Delete the presenter by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Presenter : {}", id);
        presenterRepository.deleteById(id);
    }
}
