package be.chesteric31.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StrengthMapperTest {

    private StrengthMapper strengthMapper;

    @BeforeEach
    public void setUp() {
        strengthMapper = new StrengthMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(strengthMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(strengthMapper.fromId(null)).isNull();
    }
}
