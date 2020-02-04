package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.TalkTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TalkTag} and its DTO {@link TalkTagDTO}.
 */
@Mapper(componentModel = "spring", uses = {TalkMapper.class})
public interface TalkTagMapper extends EntityMapper<TalkTagDTO, TalkTag> {


    @Mapping(target = "removeTalk", ignore = true)

    default TalkTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        TalkTag talkTag = new TalkTag();
        talkTag.setId(id);
        return talkTag;
    }
}
