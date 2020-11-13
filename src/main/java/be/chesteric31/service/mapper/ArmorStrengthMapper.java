package be.chesteric31.service.mapper;


import be.chesteric31.domain.*;
import be.chesteric31.service.dto.ArmorStrengthDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArmorStrength} and its DTO {@link ArmorStrengthDTO}.
 */
@Mapper(componentModel = "spring", uses = {StrengthMapper.class, ArmorMapper.class})
public interface ArmorStrengthMapper extends EntityMapper<ArmorStrengthDTO, ArmorStrength> {

    @Mapping(source = "strength.id", target = "strengthId")
    @Mapping(source = "armor.id", target = "armorId")
    ArmorStrengthDTO toDto(ArmorStrength armorStrength);

    @Mapping(source = "strengthId", target = "strength")
    @Mapping(source = "armorId", target = "armor")
    ArmorStrength toEntity(ArmorStrengthDTO armorStrengthDTO);

    default ArmorStrength fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArmorStrength armorStrength = new ArmorStrength();
        armorStrength.setId(id);
        return armorStrength;
    }
}
