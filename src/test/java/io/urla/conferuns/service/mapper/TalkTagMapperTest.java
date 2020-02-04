package io.urla.conferuns.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TalkTagMapperTest {

    private TalkTagMapper talkTagMapper;

    @BeforeEach
    public void setUp() {
        talkTagMapper = new TalkTagMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(talkTagMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(talkTagMapper.fromId(null)).isNull();
    }
}
