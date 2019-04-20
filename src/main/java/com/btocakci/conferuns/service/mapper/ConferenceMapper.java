package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.ConferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Conference and its DTO ConferenceDTO.
 */
@Mapper(componentModel = "spring", uses = {PlaceMapper.class, TalkMapper.class})
public interface ConferenceMapper extends EntityMapper<ConferenceDTO, Conference> {


    @Mapping(target = "scheduleItems", ignore = true)
    Conference toEntity(ConferenceDTO conferenceDTO);

    default Conference fromId(Long id) {
        if (id == null) {
            return null;
        }
        Conference conference = new Conference();
        conference.setId(id);
        return conference;
    }
}
