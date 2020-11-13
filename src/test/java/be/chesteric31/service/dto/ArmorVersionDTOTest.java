package be.chesteric31.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class ArmorVersionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmorVersionDTO.class);
        ArmorVersionDTO armorVersionDTO1 = new ArmorVersionDTO();
        armorVersionDTO1.setId(1L);
        ArmorVersionDTO armorVersionDTO2 = new ArmorVersionDTO();
        assertThat(armorVersionDTO1).isNotEqualTo(armorVersionDTO2);
        armorVersionDTO2.setId(armorVersionDTO1.getId());
        assertThat(armorVersionDTO1).isEqualTo(armorVersionDTO2);
        armorVersionDTO2.setId(2L);
        assertThat(armorVersionDTO1).isNotEqualTo(armorVersionDTO2);
        armorVersionDTO1.setId(null);
        assertThat(armorVersionDTO1).isNotEqualTo(armorVersionDTO2);
    }
}
