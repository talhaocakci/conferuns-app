package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.ScheduleItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ScheduleItem and its DTO ScheduleItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ConferenceMapper.class, TalkMapper.class})
public interface ScheduleItemMapper extends EntityMapper<ScheduleItemDTO, ScheduleItem> {

    @Mapping(source = "conference.id", target = "conferenceId")
    @Mapping(source = "talk.id", target = "talkId")
    ScheduleItemDTO toDto(ScheduleItem scheduleItem);

    @Mapping(source = "conferenceId", target = "conference")
    @Mapping(source = "talkId", target = "talk")
    ScheduleItem toEntity(ScheduleItemDTO scheduleItemDTO);

    default ScheduleItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setId(id);
        return scheduleItem;
    }
}
