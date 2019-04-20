package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.PlaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Place and its DTO PlaceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaceMapper extends EntityMapper<PlaceDTO, Place> {


    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "conferences", ignore = true)
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
