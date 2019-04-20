package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.FileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity File and its DTO FileDTO.
 */
@Mapper(componentModel = "spring", uses = {TalkMapper.class})
public interface FileMapper extends EntityMapper<FileDTO, File> {

    @Mapping(source = "talk.id", target = "talkId")
    FileDTO toDto(File file);

    @Mapping(source = "talkId", target = "talk")
    @Mapping(target = "reviews", ignore = true)
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
