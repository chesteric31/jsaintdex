package be.chesteric31.service.mapper;


import be.chesteric31.domain.*;
import be.chesteric31.service.dto.ArmorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Armor} and its DTO {@link ArmorDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArmorCategoryMapper.class})
public interface ArmorMapper extends EntityMapper<ArmorDTO, Armor> {

    @Mapping(source = "category.id", target = "categoryId")
    ArmorDTO toDto(Armor armor);

    @Mapping(target = "versions", ignore = true)
    @Mapping(target = "removeVersions", ignore = true)
    @Mapping(target = "strengths", ignore = true)
    @Mapping(target = "removeStrengths", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    Armor toEntity(ArmorDTO armorDTO);

    default Armor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Armor armor = new Armor();
        armor.setId(id);
        return armor;
    }
}
