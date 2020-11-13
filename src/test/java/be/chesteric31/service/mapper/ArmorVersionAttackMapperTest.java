package be.chesteric31.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArmorVersionAttackMapperTest {

    private ArmorVersionAttackMapper armorVersionAttackMapper;

    @BeforeEach
    public void setUp() {
        armorVersionAttackMapper = new ArmorVersionAttackMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(armorVersionAttackMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(armorVersionAttackMapper.fromId(null)).isNull();
    }
}
