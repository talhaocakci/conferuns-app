package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.TalkParticipantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TalkParticipant} and its DTO {@link TalkParticipantDTO}.
 */
@Mapper(componentModel = "spring", uses = {TalkMapper.class})
public interface TalkParticipantMapper extends EntityMapper<TalkParticipantDTO, TalkParticipant> {


    @Mapping(target = "removeTalks", ignore = true)

    default TalkParticipant fromId(Long id) {
        if (id == null) {
            return null;
        }
        TalkParticipant talkParticipant = new TalkParticipant();
        talkParticipant.setId(id);
        return talkParticipant;
    }
}
