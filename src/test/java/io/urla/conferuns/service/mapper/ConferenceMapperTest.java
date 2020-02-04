package io.urla.conferuns.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ConferenceMapperTest {

    private ConferenceMapper conferenceMapper;

    @BeforeEach
    public void setUp() {
        conferenceMapper = new ConferenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(conferenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(conferenceMapper.fromId(null)).isNull();
    }
}
