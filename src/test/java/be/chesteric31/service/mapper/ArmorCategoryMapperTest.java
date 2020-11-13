package be.chesteric31.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArmorCategoryMapperTest {

    private ArmorCategoryMapper armorCategoryMapper;

    @BeforeEach
    public void setUp() {
        armorCategoryMapper = new ArmorCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(armorCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(armorCategoryMapper.fromId(null)).isNull();
    }
}
