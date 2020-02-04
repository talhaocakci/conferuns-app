package io.urla.conferuns.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FeeMapperTest {

    private FeeMapper feeMapper;

    @BeforeEach
    public void setUp() {
        feeMapper = new FeeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(feeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(feeMapper.fromId(null)).isNull();
    }
}
