package io.urla.conferuns.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import io.urla.conferuns.domain.enumeration.ConferenceTopic;
import io.urla.conferuns.domain.enumeration.Language;

/**
 * A DTO for the {@link io.urla.conferuns.domain.Conference} entity.
 */
public class ConferenceDTO implements Serializable {

    private Long id;

    private Long conferenceId;

    private String mainName;

    private String subName;

    private ConferenceTopic mainTopic;

    private String subTopic;

    private String description;

    private Boolean isFree;

    private Language language;

    private Instant startDate;

    private Instant endDate;

    private Instant lastTalkSubmissionTime;


    private Set<PlaceDTO> places = new HashSet<>();

    private Set<TalkDTO> talks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public ConferenceTopic getMainTopic() {
        return mainTopic;
    }

    public void setMainTopic(ConferenceTopic mainTopic) {
        this.mainTopic = mainTopic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getLastTalkSubmissionTime() {
        return lastTalkSubmissionTime;
    }

    public void setLastTalkSubmissionTime(Instant lastTalkSubmissionTime) {
        this.lastTalkSubmissionTime = lastTalkSubmissionTime;
    }

    public Set<PlaceDTO> getPlaces() {
        return places;
    }

    public void setPlaces(Set<PlaceDTO> places) {
        this.places = places;
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

        ConferenceDTO conferenceDTO = (ConferenceDTO) o;
        if (conferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConferenceDTO{" +
            "id=" + getId() +
            ", conferenceId=" + getConferenceId() +
            ", mainName='" + getMainName() + "'" +
            ", subName='" + getSubName() + "'" +
            ", mainTopic='" + getMainTopic() + "'" +
            ", subTopic='" + getSubTopic() + "'" +
            ", description='" + getDescription() + "'" +
            ", isFree='" + isIsFree() + "'" +
            ", language='" + getLanguage() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", lastTalkSubmissionTime='" + getLastTalkSubmissionTime() + "'" +
            "}";
    }
}
