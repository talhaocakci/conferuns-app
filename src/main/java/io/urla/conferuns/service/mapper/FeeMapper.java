package io.urla.conferuns.service.mapper;

import io.urla.conferuns.domain.*;
import io.urla.conferuns.service.dto.FeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fee} and its DTO {@link FeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeeMapper extends EntityMapper<FeeDTO, Fee> {



    default Fee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fee fee = new Fee();
        fee.setId(id);
        return fee;
    }
}
