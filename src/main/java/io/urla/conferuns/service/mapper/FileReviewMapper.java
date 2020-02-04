package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.FileReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileReview} and its DTO {@link FileReviewDTO}.
 */
@Mapper(componentModel = "spring", uses = {FileMapper.class})
public interface FileReviewMapper extends EntityMapper<FileReviewDTO, FileReview> {

    @Mapping(source = "file.id", target = "fileId")
    FileReviewDTO toDto(FileReview fileReview);

    @Mapping(source = "fileId", target = "file")
    FileReview toEntity(FileReviewDTO fileReviewDTO);

    default FileReview fromId(Long id) {
        if (id == null) {
            return null;
        }
        FileReview fileReview = new FileReview();
        fileReview.setId(id);
        return fileReview;
    }
}
