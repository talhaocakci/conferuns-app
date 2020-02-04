package io.urla.conferuns.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class TalkParticipantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkParticipant.class);
        TalkParticipant talkParticipant1 = new TalkParticipant();
        talkParticipant1.setId(1L);
        TalkParticipant talkParticipant2 = new TalkParticipant();
        talkParticipant2.setId(talkParticipant1.getId());
        assertThat(talkParticipant1).isEqualTo(talkParticipant2);
        talkParticipant2.setId(2L);
        assertThat(talkParticipant1).isNotEqualTo(talkParticipant2);
        talkParticipant1.setId(null);
        assertThat(talkParticipant1).isNotEqualTo(talkParticipant2);
    }
}
