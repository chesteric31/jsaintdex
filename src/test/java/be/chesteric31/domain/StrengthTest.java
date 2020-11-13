package be.chesteric31.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class StrengthTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Strength.class);
        Strength strength1 = new Strength();
        strength1.setId(1L);
        Strength strength2 = new Strength();
        strength2.setId(strength1.getId());
        assertThat(strength1).isEqualTo(strength2);
        strength2.setId(2L);
        assertThat(strength1).isNotEqualTo(strength2);
        strength1.setId(null);
        assertThat(strength1).isNotEqualTo(strength2);
    }
}
