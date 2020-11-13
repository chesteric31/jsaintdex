package be.chesteric31.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class ArmorVersionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmorVersion.class);
        ArmorVersion armorVersion1 = new ArmorVersion();
        armorVersion1.setId(1L);
        ArmorVersion armorVersion2 = new ArmorVersion();
        armorVersion2.setId(armorVersion1.getId());
        assertThat(armorVersion1).isEqualTo(armorVersion2);
        armorVersion2.setId(2L);
        assertThat(armorVersion1).isNotEqualTo(armorVersion2);
        armorVersion1.setId(null);
        assertThat(armorVersion1).isNotEqualTo(armorVersion2);
    }
}
