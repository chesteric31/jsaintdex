package be.chesteric31.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class ArmorCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmorCategoryDTO.class);
        ArmorCategoryDTO armorCategoryDTO1 = new ArmorCategoryDTO();
        armorCategoryDTO1.setId(1L);
        ArmorCategoryDTO armorCategoryDTO2 = new ArmorCategoryDTO();
        assertThat(armorCategoryDTO1).isNotEqualTo(armorCategoryDTO2);
        armorCategoryDTO2.setId(armorCategoryDTO1.getId());
        assertThat(armorCategoryDTO1).isEqualTo(armorCategoryDTO2);
        armorCategoryDTO2.setId(2L);
        assertThat(armorCategoryDTO1).isNotEqualTo(armorCategoryDTO2);
        armorCategoryDTO1.setId(null);
        assertThat(armorCategoryDTO1).isNotEqualTo(armorCategoryDTO2);
    }
}
