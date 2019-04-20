package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.RoomService;
import com.btocakci.conferuns.domain.Room;
import com.btocakci.conferuns.repository.RoomRepository;
import com.btocakci.conferuns.repository.search.RoomSearchRepository;
import com.btocakci.conferuns.service.dto.RoomDTO;
import com.btocakci.conferuns.service.mapper.RoomMapper;
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
 * Service Implementation for managing Room.
 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final RoomSearchRepository roomSearchRepository;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper, RoomSearchRepository roomSearchRepository) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.roomSearchRepository = roomSearchRepository;
    }

    /**
     * Save a room.
     *
     * @param roomDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoomDTO save(RoomDTO roomDTO) {
        log.debug("Request to save Room : {}", roomDTO);
        Room room = roomMapper.toEntity(roomDTO);
        room = roomRepository.save(room);
        RoomDTO result = roomMapper.toDto(room);
        roomSearchRepository.save(room);
        return result;
    }

    /**
     * Get all the rooms.
     *
     * @return the list of entities
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
     * @param id the id of the entity
     * @return the entity
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
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Room : {}", id);
        roomRepository.deleteById(id);
        roomSearchRepository.deleteById(id);
    }

    /**
     * Search for the room corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomDTO> search(String query) {
        log.debug("Request to search Rooms for query {}", query);
        return StreamSupport
            .stream(roomSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(roomMapper::toDto)
            .collect(Collectors.toList());
    }
}
