package io.urla.conferuns.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class PresenterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PresenterDTO.class);
        PresenterDTO presenterDTO1 = new PresenterDTO();
        presenterDTO1.setId(1L);
        PresenterDTO presenterDTO2 = new PresenterDTO();
        assertThat(presenterDTO1).isNotEqualTo(presenterDTO2);
        presenterDTO2.setId(presenterDTO1.getId());
        assertThat(presenterDTO1).isEqualTo(presenterDTO2);
        presenterDTO2.setId(2L);
        assertThat(presenterDTO1).isNotEqualTo(presenterDTO2);
        presenterDTO1.setId(null);
        assertThat(presenterDTO1).isNotEqualTo(presenterDTO2);
    }
}
