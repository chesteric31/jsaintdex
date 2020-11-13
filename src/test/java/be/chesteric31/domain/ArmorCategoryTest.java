package be.chesteric31.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class ArmorCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmorCategory.class);
        ArmorCategory armorCategory1 = new ArmorCategory();
        armorCategory1.setId(1L);
        ArmorCategory armorCategory2 = new ArmorCategory();
        armorCategory2.setId(armorCategory1.getId());
        assertThat(armorCategory1).isEqualTo(armorCategory2);
        armorCategory2.setId(2L);
        assertThat(armorCategory1).isNotEqualTo(armorCategory2);
        armorCategory1.setId(null);
        assertThat(armorCategory1).isNotEqualTo(armorCategory2);
    }
}
