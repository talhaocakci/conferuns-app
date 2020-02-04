package io.urla.conferuns.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class ConferenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConferenceDTO.class);
        ConferenceDTO conferenceDTO1 = new ConferenceDTO();
        conferenceDTO1.setId(1L);
        ConferenceDTO conferenceDTO2 = new ConferenceDTO();
        assertThat(conferenceDTO1).isNotEqualTo(conferenceDTO2);
        conferenceDTO2.setId(conferenceDTO1.getId());
        assertThat(conferenceDTO1).isEqualTo(conferenceDTO2);
        conferenceDTO2.setId(2L);
        assertThat(conferenceDTO1).isNotEqualTo(conferenceDTO2);
        conferenceDTO1.setId(null);
        assertThat(conferenceDTO1).isNotEqualTo(conferenceDTO2);
    }
}
