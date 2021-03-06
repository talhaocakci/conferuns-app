package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.FileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link File} and its DTO {@link FileDTO}.
 */
@Mapper(componentModel = "spring", uses = {TalkMapper.class})
public interface FileMapper extends EntityMapper<FileDTO, File> {

    @Mapping(source = "talk.id", target = "talkId")
    FileDTO toDto(File file);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "removeReviews", ignore = true)
    @Mapping(source = "talkId", target = "talk")
    File toEntity(FileDTO fileDTO);

    default File fromId(Long id) {
        if (id == null) {
            return null;
        }
        File file = new File();
        file.setId(id);
        return file;
    }
}
