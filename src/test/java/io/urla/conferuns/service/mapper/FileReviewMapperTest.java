package io.urla.conferuns.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FileReviewMapperTest {

    private FileReviewMapper fileReviewMapper;

    @BeforeEach
    public void setUp() {
        fileReviewMapper = new FileReviewMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(fileReviewMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fileReviewMapper.fromId(null)).isNull();
    }
}
