package io.urla.conferuns.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class TalkHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkHistoryDTO.class);
        TalkHistoryDTO talkHistoryDTO1 = new TalkHistoryDTO();
        talkHistoryDTO1.setId(1L);
        TalkHistoryDTO talkHistoryDTO2 = new TalkHistoryDTO();
        assertThat(talkHistoryDTO1).isNotEqualTo(talkHistoryDTO2);
        talkHistoryDTO2.setId(talkHistoryDTO1.getId());
        assertThat(talkHistoryDTO1).isEqualTo(talkHistoryDTO2);
        talkHistoryDTO2.setId(2L);
        assertThat(talkHistoryDTO1).isNotEqualTo(talkHistoryDTO2);
        talkHistoryDTO1.setId(null);
        assertThat(talkHistoryDTO1).isNotEqualTo(talkHistoryDTO2);
    }
}
