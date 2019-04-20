package com.btocakci.conferuns.service.mapper;

import com.btocakci.conferuns.domain.*;
import com.btocakci.conferuns.service.dto.FeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fee and its DTO FeeDTO.
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
