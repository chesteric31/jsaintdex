package be.chesteric31.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class ArmorVersionAttackTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmorVersionAttack.class);
        ArmorVersionAttack armorVersionAttack1 = new ArmorVersionAttack();
        armorVersionAttack1.setId(1L);
        ArmorVersionAttack armorVersionAttack2 = new ArmorVersionAttack();
        armorVersionAttack2.setId(armorVersionAttack1.getId());
        assertThat(armorVersionAttack1).isEqualTo(armorVersionAttack2);
        armorVersionAttack2.setId(2L);
        assertThat(armorVersionAttack1).isNotEqualTo(armorVersionAttack2);
        armorVersionAttack1.setId(null);
        assertThat(armorVersionAttack1).isNotEqualTo(armorVersionAttack2);
    }
}
