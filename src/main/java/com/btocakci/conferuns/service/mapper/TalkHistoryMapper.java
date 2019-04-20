package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.TalkHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TalkHistory and its DTO TalkHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {PresenterMapper.class, TalkMapper.class})
public interface TalkHistoryMapper extends EntityMapper<TalkHistoryDTO, TalkHistory> {

    @Mapping(source = "presenter.id", target = "presenterId")
    @Mapping(source = "talk.id", target = "talkId")
    TalkHistoryDTO toDto(TalkHistory talkHistory);

    @Mapping(source = "presenterId", target = "presenter")
    @Mapping(source = "talkId", target = "talk")
    TalkHistory toEntity(TalkHistoryDTO talkHistoryDTO);

    default TalkHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        TalkHistory talkHistory = new TalkHistory();
        talkHistory.setId(id);
        return talkHistory;
    }
}
