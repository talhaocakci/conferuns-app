package io.urla.conferuns.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TalkParticipantMapperTest {

    private TalkParticipantMapper talkParticipantMapper;

    @BeforeEach
    public void setUp() {
        talkParticipantMapper = new TalkParticipantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(talkParticipantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(talkParticipantMapper.fromId(null)).isNull();
    }
}
