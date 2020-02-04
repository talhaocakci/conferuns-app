package io.urla.conferuns.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TalkMapperTest {

    private TalkMapper talkMapper;

    @BeforeEach
    public void setUp() {
        talkMapper = new TalkMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(talkMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(talkMapper.fromId(null)).isNull();
    }
}
