package io.urla.conferuns.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TalkHistoryMapperTest {

    private TalkHistoryMapper talkHistoryMapper;

    @BeforeEach
    public void setUp() {
        talkHistoryMapper = new TalkHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(talkHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(talkHistoryMapper.fromId(null)).isNull();
    }
}
