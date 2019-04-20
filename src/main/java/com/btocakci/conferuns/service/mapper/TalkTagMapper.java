package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.TalkTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TalkTag and its DTO TalkTagDTO.
 */
@Mapper(componentModel = "spring", uses = {TalkMapper.class})
public interface TalkTagMapper extends EntityMapper<TalkTagDTO, TalkTag> {



    default TalkTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        TalkTag talkTag = new TalkTag();
        talkTag.setId(id);
        return talkTag;
    }
}
