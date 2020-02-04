package io.urla.conferuns.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.urla.conferuns.web.rest.TestUtil;

public class PresenterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Presenter.class);
        Presenter presenter1 = new Presenter();
        presenter1.setId(1L);
        Presenter presenter2 = new Presenter();
        presenter2.setId(presenter1.getId());
        assertThat(presenter1).isEqualTo(presenter2);
        presenter2.setId(2L);
        assertThat(presenter1).isNotEqualTo(presenter2);
        presenter1.setId(null);
        assertThat(presenter1).isNotEqualTo(presenter2);
    }
}
