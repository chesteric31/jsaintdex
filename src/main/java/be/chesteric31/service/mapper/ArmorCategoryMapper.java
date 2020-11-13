package be.chesteric31.service.mapper;


import be.chesteric31.domain.*;
import be.chesteric31.service.dto.ArmorCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArmorCategory} and its DTO {@link ArmorCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArmorCategoryMapper extends EntityMapper<ArmorCategoryDTO, ArmorCategory> {



    default ArmorCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArmorCategory armorCategory = new ArmorCategory();
        armorCategory.setId(id);
        return armorCategory;
    }
}
