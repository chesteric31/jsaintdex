package be.chesteric31.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArmorStrengthMapperTest {

    private ArmorStrengthMapper armorStrengthMapper;

    @BeforeEach
    public void setUp() {
        armorStrengthMapper = new ArmorStrengthMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(armorStrengthMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(armorStrengthMapper.fromId(null)).isNull();
    }
}
