package be.chesteric31.service.mapper;


import be.chesteric31.domain.*;
import be.chesteric31.service.dto.ArmorVersionAttackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArmorVersionAttack} and its DTO {@link ArmorVersionAttackDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArmorVersionMapper.class})
public interface ArmorVersionAttackMapper extends EntityMapper<ArmorVersionAttackDTO, ArmorVersionAttack> {

    @Mapping(source = "version.id", target = "versionId")
    ArmorVersionAttackDTO toDto(ArmorVersionAttack armorVersionAttack);

    @Mapping(source = "versionId", target = "version")
    ArmorVersionAttack toEntity(ArmorVersionAttackDTO armorVersionAttackDTO);

    default ArmorVersionAttack fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArmorVersionAttack armorVersionAttack = new ArmorVersionAttack();
        armorVersionAttack.setId(id);
        return armorVersionAttack;
    }
}
