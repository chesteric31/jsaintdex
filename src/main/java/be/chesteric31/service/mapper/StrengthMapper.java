package be.chesteric31.service.mapper;


import be.chesteric31.domain.*;
import be.chesteric31.service.dto.StrengthDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Strength} and its DTO {@link StrengthDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StrengthMapper extends EntityMapper<StrengthDTO, Strength> {



    default Strength fromId(Long id) {
        if (id == null) {
            return null;
        }
        Strength strength = new Strength();
        strength.setId(id);
        return strength;
    }
}
