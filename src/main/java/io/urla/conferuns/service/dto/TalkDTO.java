package io.urla.conferuns.service.dto;
import java.io.Serializable;
import java.util.Objects;
import io.urla.conferuns.domain.enumeration.Language;
import io.urla.conferuns.domain.enumeration.TalkStatus;

/**
 * A DTO for the {@link io.urla.conferuns.domain.Talk} entity.
 */
public class TalkDTO implements Serializable {

    private Long id;

    private Language language;

    private String mainTopic;

    private String subTopic;

    private TalkStatus status;


    private Long presenterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getMainTopic() {
        return mainTopic;
    }

    public void setMainTopic(String mainTopic) {
        this.mainTopic = mainTopic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public TalkStatus getStatus() {
        return status;
    }

    public void setStatus(TalkStatus status) {
        this.status = status;
    }

    public Long getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(Long presenterId) {
        this.presenterId = presenterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TalkDTO talkDTO = (TalkDTO) o;
        if (talkDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), talkDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TalkDTO{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", mainTopic='" + getMainTopic() + "'" +
            ", subTopic='" + getSubTopic() + "'" +
            ", status='" + getStatus() + "'" +
            ", presenterId=" + getPresenterId() +
            "}";
    }
}
