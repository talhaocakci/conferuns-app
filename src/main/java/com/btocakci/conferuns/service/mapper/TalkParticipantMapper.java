package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.TalkParticipantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TalkParticipant and its DTO TalkParticipantDTO.
 */
@Mapper(componentModel = "spring", uses = {TalkMapper.class})
public interface TalkParticipantMapper extends EntityMapper<TalkParticipantDTO, TalkParticipant> {



    default TalkParticipant fromId(Long id) {
        if (id == null) {
            return null;
        }
        TalkParticipant talkParticipant = new TalkParticipant();
        talkParticipant.setId(id);
        return talkParticipant;
    }
}
