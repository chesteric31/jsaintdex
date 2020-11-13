package be.chesteric31.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class ArmorStrengthDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmorStrengthDTO.class);
        ArmorStrengthDTO armorStrengthDTO1 = new ArmorStrengthDTO();
        armorStrengthDTO1.setId(1L);
        ArmorStrengthDTO armorStrengthDTO2 = new ArmorStrengthDTO();
        assertThat(armorStrengthDTO1).isNotEqualTo(armorStrengthDTO2);
        armorStrengthDTO2.setId(armorStrengthDTO1.getId());
        assertThat(armorStrengthDTO1).isEqualTo(armorStrengthDTO2);
        armorStrengthDTO2.setId(2L);
        assertThat(armorStrengthDTO1).isNotEqualTo(armorStrengthDTO2);
        armorStrengthDTO1.setId(null);
        assertThat(armorStrengthDTO1).isNotEqualTo(armorStrengthDTO2);
    }
}
