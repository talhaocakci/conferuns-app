package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.RoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Room} and its DTO {@link RoomDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlaceMapper.class})
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {

    @Mapping(source = "place.id", target = "placeId")
    RoomDTO toDto(Room room);

    @Mapping(source = "placeId", target = "place")
    Room toEntity(RoomDTO roomDTO);

    default Room fromId(Long id) {
        if (id == null) {
            return null;
        }
        Room room = new Room();
        room.setId(id);
        return room;
    }
}
