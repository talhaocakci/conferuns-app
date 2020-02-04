package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.TalkHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TalkHistory} and its DTO {@link TalkHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {TalkMapper.class, PresenterMapper.class})
public interface TalkHistoryMapper extends EntityMapper<TalkHistoryDTO, TalkHistory> {

    @Mapping(source = "talk.id", target = "talkId")
    @Mapping(source = "presenter.id", target = "presenterId")
    TalkHistoryDTO toDto(TalkHistory talkHistory);

    @Mapping(source = "talkId", target = "talk")
    @Mapping(source = "presenterId", target = "presenter")
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
