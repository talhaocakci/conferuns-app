package io.urla.conferuns.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class FeeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeeDTO.class);
        FeeDTO feeDTO1 = new FeeDTO();
        feeDTO1.setId(1L);
        FeeDTO feeDTO2 = new FeeDTO();
        assertThat(feeDTO1).isNotEqualTo(feeDTO2);
        feeDTO2.setId(feeDTO1.getId());
        assertThat(feeDTO1).isEqualTo(feeDTO2);
        feeDTO2.setId(2L);
        assertThat(feeDTO1).isNotEqualTo(feeDTO2);
        feeDTO1.setId(null);
        assertThat(feeDTO1).isNotEqualTo(feeDTO2);
    }
}
