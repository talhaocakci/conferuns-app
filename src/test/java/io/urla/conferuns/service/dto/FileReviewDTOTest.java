package io.urla.conferuns.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class FileReviewDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileReviewDTO.class);
        FileReviewDTO fileReviewDTO1 = new FileReviewDTO();
        fileReviewDTO1.setId(1L);
        FileReviewDTO fileReviewDTO2 = new FileReviewDTO();
        assertThat(fileReviewDTO1).isNotEqualTo(fileReviewDTO2);
        fileReviewDTO2.setId(fileReviewDTO1.getId());
        assertThat(fileReviewDTO1).isEqualTo(fileReviewDTO2);
        fileReviewDTO2.setId(2L);
        assertThat(fileReviewDTO1).isNotEqualTo(fileReviewDTO2);
        fileReviewDTO1.setId(null);
        assertThat(fileReviewDTO1).isNotEqualTo(fileReviewDTO2);
    }
}
