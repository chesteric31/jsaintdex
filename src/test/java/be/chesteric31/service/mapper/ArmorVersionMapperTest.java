package be.chesteric31.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArmorVersionMapperTest {

    private ArmorVersionMapper armorVersionMapper;

    @BeforeEach
    public void setUp() {
        armorVersionMapper = new ArmorVersionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(armorVersionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(armorVersionMapper.fromId(null)).isNull();
    }
}
