package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.RoomService;
import io.urla.conferuns.domain.Room;
import io.urla.conferuns.repository.RoomRepository;
import io.urla.conferuns.service.dto.RoomDTO;
import io.urla.conferuns.service.mapper.RoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Room}.
 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    /**
     * Save a room.
     *
     * @param roomDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RoomDTO save(RoomDTO roomDTO) {
        log.debug("Request to save Room : {}", roomDTO);
        Room room = roomMapper.toEntity(roomDTO);
        room = roomRepository.save(room);
        return roomMapper.toDto(room);
    }

    /**
     * Get all the rooms.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomDTO> findAll() {
        log.debug("Request to get all Rooms");
        return roomRepository.findAll().stream()
            .map(roomMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one room by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoomDTO> findOne(Long id) {
        log.debug("Request to get Room : {}", id);
        return roomRepository.findById(id)
            .map(roomMapper::toDto);
    }

    /**
     * Delete the room by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Room : {}", id);
        roomRepository.deleteById(id);
    }
}
