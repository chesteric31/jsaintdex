package be.chesteric31.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class ArmorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmorDTO.class);
        ArmorDTO armorDTO1 = new ArmorDTO();
        armorDTO1.setId(1L);
        ArmorDTO armorDTO2 = new ArmorDTO();
        assertThat(armorDTO1).isNotEqualTo(armorDTO2);
        armorDTO2.setId(armorDTO1.getId());
        assertThat(armorDTO1).isEqualTo(armorDTO2);
        armorDTO2.setId(2L);
        assertThat(armorDTO1).isNotEqualTo(armorDTO2);
        armorDTO1.setId(null);
        assertThat(armorDTO1).isNotEqualTo(armorDTO2);
    }
}
