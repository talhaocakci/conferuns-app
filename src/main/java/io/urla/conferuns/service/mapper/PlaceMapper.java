package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.PlaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Place} and its DTO {@link PlaceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaceMapper extends EntityMapper<PlaceDTO, Place> {


    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "removeRooms", ignore = true)
    @Mapping(target = "conferences", ignore = true)
    @Mapping(target = "removeConferences", ignore = true)
    Place toEntity(PlaceDTO placeDTO);

    default Place fromId(Long id) {
        if (id == null) {
            return null;
        }
        Place place = new Place();
        place.setId(id);
        return place;
    }
}
