package com.btocakci.conferuns.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Presenter entity.
 */
public class PresenterDTO implements Serializable {

    private Long id;

    private Long presenterId;

    private Double totalTechnicalPoints;

    private Double averageTechnicalPoints;

    private Double totalSpeakingPoints;

    private Double averageSpeakingPoints;

    private Double totalExcitementPoints;

    private Double averageExcitementPoints;


    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(Long presenterId) {
        this.presenterId = presenterId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PresenterDTO presenterDTO = (PresenterDTO) o;
        if (presenterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), presenterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PresenterDTO{" +
            "id=" + getId() +
            ", presenterId=" + getPresenterId() +
            ", totalTechnicalPoints=" + getTotalTechnicalPoints() +
            ", averageTechnicalPoints=" + getAverageTechnicalPoints() +
            ", totalSpeakingPoints=" + getTotalSpeakingPoints() +
            ", averageSpeakingPoints=" + getAverageSpeakingPoints() +
            ", totalExcitementPoints=" + getTotalExcitementPoints() +
            ", averageExcitementPoints=" + getAverageExcitementPoints() +
            ", user=" + getUserId() +
            "}";
    }
}
