package io.urla.conferuns.service.dto;
import java.io.Serializable;
import java.util.Objects;
import io.urla.conferuns.domain.enumeration.ConferenceTopic;
import io.urla.conferuns.domain.enumeration.Audience;

/**
 * A DTO for the {@link io.urla.conferuns.domain.Subject} entity.
 */
public class SubjectDTO implements Serializable {

    private Long id;

    private ConferenceTopic topic;

    private Integer difficulty;

    private Audience audience;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConferenceTopic getTopic() {
        return topic;
    }

    public void setTopic(ConferenceTopic topic) {
        this.topic = topic;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubjectDTO subjectDTO = (SubjectDTO) o;
        if (subjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubjectDTO{" +
            "id=" + getId() +
            ", topic='" + getTopic() + "'" +
            ", difficulty=" + getDifficulty() +
            ", audience='" + getAudience() + "'" +
            "}";
    }
}
