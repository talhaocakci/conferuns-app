package io.urla.conferuns.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class ScheduleItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleItem.class);
        ScheduleItem scheduleItem1 = new ScheduleItem();
        scheduleItem1.setId(1L);
        ScheduleItem scheduleItem2 = new ScheduleItem();
        scheduleItem2.setId(scheduleItem1.getId());
        assertThat(scheduleItem1).isEqualTo(scheduleItem2);
        scheduleItem2.setId(2L);
        assertThat(scheduleItem1).isNotEqualTo(scheduleItem2);
        scheduleItem1.setId(null);
        assertThat(scheduleItem1).isNotEqualTo(scheduleItem2);
    }
}
