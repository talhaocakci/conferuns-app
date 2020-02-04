package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.ScheduleItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScheduleItem} and its DTO {@link ScheduleItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {TalkMapper.class, ConferenceMapper.class})
public interface ScheduleItemMapper extends EntityMapper<ScheduleItemDTO, ScheduleItem> {

    @Mapping(source = "talk.id", target = "talkId")
    @Mapping(source = "conference.id", target = "conferenceId")
    ScheduleItemDTO toDto(ScheduleItem scheduleItem);

    @Mapping(source = "talkId", target = "talk")
    @Mapping(source = "conferenceId", target = "conference")
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
