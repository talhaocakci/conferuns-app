package io.urla.conferuns.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ScheduleItemMapperTest {

    private ScheduleItemMapper scheduleItemMapper;

    @BeforeEach
    public void setUp() {
        scheduleItemMapper = new ScheduleItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(scheduleItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scheduleItemMapper.fromId(null)).isNull();
    }
}
