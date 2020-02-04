package io.urla.conferuns.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class TalkTagDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkTagDTO.class);
        TalkTagDTO talkTagDTO1 = new TalkTagDTO();
        talkTagDTO1.setId(1L);
        TalkTagDTO talkTagDTO2 = new TalkTagDTO();
        assertThat(talkTagDTO1).isNotEqualTo(talkTagDTO2);
        talkTagDTO2.setId(talkTagDTO1.getId());
        assertThat(talkTagDTO1).isEqualTo(talkTagDTO2);
        talkTagDTO2.setId(2L);
        assertThat(talkTagDTO1).isNotEqualTo(talkTagDTO2);
        talkTagDTO1.setId(null);
        assertThat(talkTagDTO1).isNotEqualTo(talkTagDTO2);
    }
}
