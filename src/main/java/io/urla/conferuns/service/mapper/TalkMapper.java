package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.TalkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Talk} and its DTO {@link TalkDTO}.
 */
@Mapper(componentModel = "spring", uses = {PresenterMapper.class})
public interface TalkMapper extends EntityMapper<TalkDTO, Talk> {

    @Mapping(source = "presenter.id", target = "presenterId")
    TalkDTO toDto(Talk talk);

    @Mapping(source = "presenterId", target = "presenter")
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "removeFiles", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "removeParticipants", ignore = true)
    @Mapping(target = "conferences", ignore = true)
    @Mapping(target = "removeConferences", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "removeTags", ignore = true)
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
