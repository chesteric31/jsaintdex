package be.chesteric31.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class ArmorStrengthTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmorStrength.class);
        ArmorStrength armorStrength1 = new ArmorStrength();
        armorStrength1.setId(1L);
        ArmorStrength armorStrength2 = new ArmorStrength();
        armorStrength2.setId(armorStrength1.getId());
        assertThat(armorStrength1).isEqualTo(armorStrength2);
        armorStrength2.setId(2L);
        assertThat(armorStrength1).isNotEqualTo(armorStrength2);
        armorStrength1.setId(null);
        assertThat(armorStrength1).isNotEqualTo(armorStrength2);
    }
}
