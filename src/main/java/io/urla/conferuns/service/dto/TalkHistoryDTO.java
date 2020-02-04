package io.urla.conferuns.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.urla.conferuns.domain.TalkHistory} entity.
 */
public class TalkHistoryDTO implements Serializable {

    private Long id;

    private Instant date;

    private Long totalAudience;

    private Double totalTechnicalPoints;

    private Double averageTechnicalPoints;

    private Double totalSpeakingPoints;

    private Double averageSpeakingPoints;

    private Double totalExcitementPoints;

    private Double averageExcitementPoints;


    private Long talkId;

    private Long presenterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getTotalAudience() {
        return totalAudience;
    }

    public void setTotalAudience(Long totalAudience) {
        this.totalAudience = totalAudience;
    }

    public Double getTotalTechnicalPoints() {
        return totalTechnicalPoints;
    }

    public void setTotalTechnicalPoints(Double totalTechnicalPoints) {
        this.totalTechnicalPoints = totalTechnicalPoints;
    }

    public Double getAverageTechnicalPoints() {
        return averageTechnicalPoints;
    }

    public void setAverageTechnicalPoints(Double averageTechnicalPoints) {
        this.averageTechnicalPoints = averageTechnicalPoints;
    }

    public Double getTotalSpeakingPoints() {
        return totalSpeakingPoints;
    }

    public void setTotalSpeakingPoints(Double totalSpeakingPoints) {
        this.totalSpeakingPoints = totalSpeakingPoints;
    }

    public Double getAverageSpeakingPoints() {
        return averageSpeakingPoints;
    }

    public void setAverageSpeakingPoints(Double averageSpeakingPoints) {
        this.averageSpeakingPoints = averageSpeakingPoints;
    }

    public Double getTotalExcitementPoints() {
        return totalExcitementPoints;
    }

    public void setTotalExcitementPoints(Double totalExcitementPoints) {
        this.totalExcitementPoints = totalExcitementPoints;
    }

    public Double getAverageExcitementPoints() {
        return averageExcitementPoints;
    }

    public void setAverageExcitementPoints(Double averageExcitementPoints) {
        this.averageExcitementPoints = averageExcitementPoints;
    }

    public Long getTalkId() {
        return talkId;
    }

    public void setTalkId(Long talkId) {
        this.talkId = talkId;
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

        TalkHistoryDTO talkHistoryDTO = (TalkHistoryDTO) o;
        if (talkHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), talkHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TalkHistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", totalAudience=" + getTotalAudience() +
            ", totalTechnicalPoints=" + getTotalTechnicalPoints() +
            ", averageTechnicalPoints=" + getAverageTechnicalPoints() +
            ", totalSpeakingPoints=" + getTotalSpeakingPoints() +
            ", averageSpeakingPoints=" + getAverageSpeakingPoints() +
            ", totalExcitementPoints=" + getTotalExcitementPoints() +
            ", averageExcitementPoints=" + getAverageExcitementPoints() +
            ", talkId=" + getTalkId() +
            ", presenterId=" + getPresenterId() +
            "}";
    }
}
