package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.PresenterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Presenter and its DTO PresenterDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PresenterMapper extends EntityMapper<PresenterDTO, Presenter> {

    @Mapping(source = "user.id", target = "userId")
    PresenterDTO toDto(Presenter presenter);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "talks", ignore = true)
    Presenter toEntity(PresenterDTO presenterDTO);

    default Presenter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Presenter presenter = new Presenter();
        presenter.setId(id);
        return presenter;
    }
}
