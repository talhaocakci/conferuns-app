package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.TalkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Talk and its DTO TalkDTO.
 */
@Mapper(componentModel = "spring", uses = {PresenterMapper.class})
public interface TalkMapper extends EntityMapper<TalkDTO, Talk> {

    @Mapping(source = "presenter.id", target = "presenterId")
    TalkDTO toDto(Talk talk);

    @Mapping(source = "presenterId", target = "presenter")
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "conferences", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Talk toEntity(TalkDTO talkDTO);

    default Talk fromId(Long id) {
        if (id == null) {
            return null;
        }
        Talk talk = new Talk();
        talk.setId(id);
        return talk;
    }
}
