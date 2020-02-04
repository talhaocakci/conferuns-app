package io.urla.conferuns.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class TalkParticipantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkParticipantDTO.class);
        TalkParticipantDTO talkParticipantDTO1 = new TalkParticipantDTO();
        talkParticipantDTO1.setId(1L);
        TalkParticipantDTO talkParticipantDTO2 = new TalkParticipantDTO();
        assertThat(talkParticipantDTO1).isNotEqualTo(talkParticipantDTO2);
        talkParticipantDTO2.setId(talkParticipantDTO1.getId());
        assertThat(talkParticipantDTO1).isEqualTo(talkParticipantDTO2);
        talkParticipantDTO2.setId(2L);
        assertThat(talkParticipantDTO1).isNotEqualTo(talkParticipantDTO2);
        talkParticipantDTO1.setId(null);
        assertThat(talkParticipantDTO1).isNotEqualTo(talkParticipantDTO2);
    }
}
