package be.chesteric31.service.mapper;


import be.chesteric31.domain.*;
import be.chesteric31.service.dto.ArmorVersionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArmorVersion} and its DTO {@link ArmorVersionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArmorMapper.class})
public interface ArmorVersionMapper extends EntityMapper<ArmorVersionDTO, ArmorVersion> {

    @Mapping(source = "armor.id", target = "armorId")
    ArmorVersionDTO toDto(ArmorVersion armorVersion);

    @Mapping(target = "attacks", ignore = true)
    @Mapping(target = "removeAttacks", ignore = true)
    @Mapping(source = "armorId", target = "armor")
    ArmorVersion toEntity(ArmorVersionDTO armorVersionDTO);

    default ArmorVersion fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArmorVersion armorVersion = new ArmorVersion();
        armorVersion.setId(id);
        return armorVersion;
    }
}
