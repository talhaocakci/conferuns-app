package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.ConferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conference} and its DTO {@link ConferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlaceMapper.class, TalkMapper.class})
public interface ConferenceMapper extends EntityMapper<ConferenceDTO, Conference> {


    @Mapping(target = "scheduleItems", ignore = true)
    @Mapping(target = "removeScheduleItems", ignore = true)
    @Mapping(target = "removePlaces", ignore = true)
    @Mapping(target = "removeTalks", ignore = true)
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
