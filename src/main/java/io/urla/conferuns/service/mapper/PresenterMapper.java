package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.PresenterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Presenter} and its DTO {@link PresenterDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PresenterMapper extends EntityMapper<PresenterDTO, Presenter> {

    @Mapping(source = "user.id", target = "userId")
    PresenterDTO toDto(Presenter presenter);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "talks", ignore = true)
    @Mapping(target = "removeTalks", ignore = true)
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
