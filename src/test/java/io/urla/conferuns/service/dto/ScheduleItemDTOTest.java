package io.urla.conferuns.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class ScheduleItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleItemDTO.class);
        ScheduleItemDTO scheduleItemDTO1 = new ScheduleItemDTO();
        scheduleItemDTO1.setId(1L);
        ScheduleItemDTO scheduleItemDTO2 = new ScheduleItemDTO();
        assertThat(scheduleItemDTO1).isNotEqualTo(scheduleItemDTO2);
        scheduleItemDTO2.setId(scheduleItemDTO1.getId());
        assertThat(scheduleItemDTO1).isEqualTo(scheduleItemDTO2);
        scheduleItemDTO2.setId(2L);
        assertThat(scheduleItemDTO1).isNotEqualTo(scheduleItemDTO2);
        scheduleItemDTO1.setId(null);
        assertThat(scheduleItemDTO1).isNotEqualTo(scheduleItemDTO2);
    }
}
