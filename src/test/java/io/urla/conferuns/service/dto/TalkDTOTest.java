package io.urla.conferuns.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class TalkDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkDTO.class);
        TalkDTO talkDTO1 = new TalkDTO();
        talkDTO1.setId(1L);
        TalkDTO talkDTO2 = new TalkDTO();
        assertThat(talkDTO1).isNotEqualTo(talkDTO2);
        talkDTO2.setId(talkDTO1.getId());
        assertThat(talkDTO1).isEqualTo(talkDTO2);
        talkDTO2.setId(2L);
        assertThat(talkDTO1).isNotEqualTo(talkDTO2);
        talkDTO1.setId(null);
        assertThat(talkDTO1).isNotEqualTo(talkDTO2);
    }
}
