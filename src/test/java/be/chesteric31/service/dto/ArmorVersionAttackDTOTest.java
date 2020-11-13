package be.chesteric31.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class ArmorVersionAttackDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmorVersionAttackDTO.class);
        ArmorVersionAttackDTO armorVersionAttackDTO1 = new ArmorVersionAttackDTO();
        armorVersionAttackDTO1.setId(1L);
        ArmorVersionAttackDTO armorVersionAttackDTO2 = new ArmorVersionAttackDTO();
        assertThat(armorVersionAttackDTO1).isNotEqualTo(armorVersionAttackDTO2);
        armorVersionAttackDTO2.setId(armorVersionAttackDTO1.getId());
        assertThat(armorVersionAttackDTO1).isEqualTo(armorVersionAttackDTO2);
        armorVersionAttackDTO2.setId(2L);
        assertThat(armorVersionAttackDTO1).isNotEqualTo(armorVersionAttackDTO2);
        armorVersionAttackDTO1.setId(null);
        assertThat(armorVersionAttackDTO1).isNotEqualTo(armorVersionAttackDTO2);
    }
}
