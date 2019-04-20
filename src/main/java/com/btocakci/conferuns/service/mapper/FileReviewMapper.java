package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.FileReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FileReview and its DTO FileReviewDTO.
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
