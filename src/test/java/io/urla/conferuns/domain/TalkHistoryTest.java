package io.urla.conferuns.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class TalkHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkHistory.class);
        TalkHistory talkHistory1 = new TalkHistory();
        talkHistory1.setId(1L);
        TalkHistory talkHistory2 = new TalkHistory();
        talkHistory2.setId(talkHistory1.getId());
        assertThat(talkHistory1).isEqualTo(talkHistory2);
        talkHistory2.setId(2L);
        assertThat(talkHistory1).isNotEqualTo(talkHistory2);
        talkHistory1.setId(null);
        assertThat(talkHistory1).isNotEqualTo(talkHistory2);
    }
}
