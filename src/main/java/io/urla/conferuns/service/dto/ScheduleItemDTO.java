package io.urla.conferuns.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.urla.conferuns.domain.ScheduleItem} entity.
 */
public class ScheduleItemDTO implements Serializable {

    private Long id;

    private Instant fromTime;

    private Instant tillTime;


    private Long talkId;

    private Long conferenceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public void setFromTime(Instant fromTime) {
        this.fromTime = fromTime;
    }

    public Instant getTillTime() {
        return tillTime;
    }

    public void setTillTime(Instant tillTime) {
        this.tillTime = tillTime;
    }

    public Long getTalkId() {
        return talkId;
    }

    public void setTalkId(Long talkId) {
        this.talkId = talkId;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScheduleItemDTO scheduleItemDTO = (ScheduleItemDTO) o;
        if (scheduleItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheduleItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScheduleItemDTO{" +
            "id=" + getId() +
            ", fromTime='" + getFromTime() + "'" +
            ", tillTime='" + getTillTime() + "'" +
            ", talkId=" + getTalkId() +
            ", conferenceId=" + getConferenceId() +
            "}";
    }
}
