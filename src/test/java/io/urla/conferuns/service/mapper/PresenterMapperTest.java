package io.urla.conferuns.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PresenterMapperTest {

    private PresenterMapper presenterMapper;

    @BeforeEach
    public void setUp() {
        presenterMapper = new PresenterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(presenterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(presenterMapper.fromId(null)).isNull();
    }
}
