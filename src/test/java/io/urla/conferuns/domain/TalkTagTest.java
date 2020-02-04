package io.urla.conferuns.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class TalkTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkTag.class);
        TalkTag talkTag1 = new TalkTag();
        talkTag1.setId(1L);
        TalkTag talkTag2 = new TalkTag();
        talkTag2.setId(talkTag1.getId());
        assertThat(talkTag1).isEqualTo(talkTag2);
        talkTag2.setId(2L);
        assertThat(talkTag1).isNotEqualTo(talkTag2);
        talkTag1.setId(null);
        assertThat(talkTag1).isNotEqualTo(talkTag2);
    }
}
