package io.urla.conferuns.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class FileReviewTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileReview.class);
        FileReview fileReview1 = new FileReview();
        fileReview1.setId(1L);
        FileReview fileReview2 = new FileReview();
        fileReview2.setId(fileReview1.getId());
        assertThat(fileReview1).isEqualTo(fileReview2);
        fileReview2.setId(2L);
        assertThat(fileReview1).isNotEqualTo(fileReview2);
        fileReview1.setId(null);
        assertThat(fileReview1).isNotEqualTo(fileReview2);
    }
}
