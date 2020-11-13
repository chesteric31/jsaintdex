package be.chesteric31.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import be.chesteric31.web.rest.TestUtil;

public class StrengthDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StrengthDTO.class);
        StrengthDTO strengthDTO1 = new StrengthDTO();
        strengthDTO1.setId(1L);
        StrengthDTO strengthDTO2 = new StrengthDTO();
        assertThat(strengthDTO1).isNotEqualTo(strengthDTO2);
        strengthDTO2.setId(strengthDTO1.getId());
        assertThat(strengthDTO1).isEqualTo(strengthDTO2);
        strengthDTO2.setId(2L);
        assertThat(strengthDTO1).isNotEqualTo(strengthDTO2);
        strengthDTO1.setId(null);
        assertThat(strengthDTO1).isNotEqualTo(strengthDTO2);
    }
}
