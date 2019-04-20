package com.btocakci.conferuns.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TalkParticipant entity.
 */
public class TalkParticipantDTO implements Serializable {

    private Long id;

    private Boolean checkedIn;

    private Boolean plannedToGo;

    private Boolean favorited;


    private Set<TalkDTO> talks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(Boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Boolean isPlannedToGo() {
        return plannedToGo;
    }

    public void setPlannedToGo(Boolean plannedToGo) {
        this.plannedToGo = plannedToGo;
    }

    public Boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Set<TalkDTO> getTalks() {
        return talks;
    }

    public void setTalks(Set<TalkDTO> talks) {
        this.talks = talks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TalkParticipantDTO talkParticipantDTO = (TalkParticipantDTO) o;
        if (talkParticipantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), talkParticipantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TalkParticipantDTO{" +
            "id=" + getId() +
            ", checkedIn='" + isCheckedIn() + "'" +
            ", plannedToGo='" + isPlannedToGo() + "'" +
            ", favorited='" + isFavorited() + "'" +
            "}";
    }
}
