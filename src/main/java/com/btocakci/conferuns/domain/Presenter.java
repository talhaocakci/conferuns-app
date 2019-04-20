package com.btocakci.conferuns.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Presenter.
 */
@Entity
@Table(name = "presenter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "presenter")
public class Presenter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "presenter_id")
    private Long presenterId;

    @Column(name = "total_technical_points")
    private Double totalTechnicalPoints;

    @Column(name = "average_technical_points")
    private Double averageTechnicalPoints;

    @Column(name = "total_speaking_points")
    private Double totalSpeakingPoints;

    @Column(name = "average_speaking_points")
    private Double averageSpeakingPoints;

    @Column(name = "total_excitement_points")
    private Double totalExcitementPoints;

    @Column(name = "average_excitement_points")
    private Double averageExcitementPoints;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "presenter")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TalkHistory> talks = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPresenterId() {
        return presenterId;
    }

    public Presenter presenterId(Long presenterId) {
        this.presenterId = presenterId;
        return this;
    }

    public void setPresenterId(Long presenterId) {
        this.presenterId = presenterId;
    }

    public Double getTotalTechnicalPoints() {
        return totalTechnicalPoints;
    }

    public Presenter totalTechnicalPoints(Double totalTechnicalPoints) {
        this.totalTechnicalPoints = totalTechnicalPoints;
        return this;
    }

    public void setTotalTechnicalPoints(Double totalTechnicalPoints) {
        this.totalTechnicalPoints = totalTechnicalPoints;
    }

    public Double getAverageTechnicalPoints() {
        return averageTechnicalPoints;
    }

    public Presenter averageTechnicalPoints(Double averageTechnicalPoints) {
        this.averageTechnicalPoints = averageTechnicalPoints;
        return this;
    }

    public void setAverageTechnicalPoints(Double averageTechnicalPoints) {
        this.averageTechnicalPoints = averageTechnicalPoints;
    }

    public Double getTotalSpeakingPoints() {
        return totalSpeakingPoints;
    }

    public Presenter totalSpeakingPoints(Double totalSpeakingPoints) {
        this.totalSpeakingPoints = totalSpeakingPoints;
        return this;
    }

    public void setTotalSpeakingPoints(Double totalSpeakingPoints) {
        this.totalSpeakingPoints = totalSpeakingPoints;
    }

    public Double getAverageSpeakingPoints() {
        return averageSpeakingPoints;
    }

    public Presenter averageSpeakingPoints(Double averageSpeakingPoints) {
        this.averageSpeakingPoints = averageSpeakingPoints;
        return this;
    }

    public void setAverageSpeakingPoints(Double averageSpeakingPoints) {
        this.averageSpeakingPoints = averageSpeakingPoints;
    }

    public Double getTotalExcitementPoints() {
        return totalExcitementPoints;
    }

    public Presenter totalExcitementPoints(Double totalExcitementPoints) {
        this.totalExcitementPoints = totalExcitementPoints;
        return this;
    }

    public void setTotalExcitementPoints(Double totalExcitementPoints) {
        this.totalExcitementPoints = totalExcitementPoints;
    }

    public Double getAverageExcitementPoints() {
        return averageExcitementPoints;
    }

    public Presenter averageExcitementPoints(Double averageExcitementPoints) {
        this.averageExcitementPoints = averageExcitementPoints;
        return this;
    }

    public void setAverageExcitementPoints(Double averageExcitementPoints) {
        this.averageExcitementPoints = averageExcitementPoints;
    }

    public User getUser() {
        return user;
    }

    public Presenter user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<TalkHistory> getTalks() {
        return talks;
    }

    public Presenter talks(Set<TalkHistory> talkHistories) {
        this.talks = talkHistories;
        return this;
    }

    public Presenter addTalks(TalkHistory talkHistory) {
        this.talks.add(talkHistory);
        talkHistory.setPresenter(this);
        return this;
    }

    public Presenter removeTalks(TalkHistory talkHistory) {
        this.talks.remove(talkHistory);
        talkHistory.setPresenter(null);
        return this;
    }

    public void setTalks(Set<TalkHistory> talkHistories) {
        this.talks = talkHistories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Presenter presenter = (Presenter) o;
        if (presenter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), presenter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Presenter{" +
            "id=" + getId() +
            ", presenterId=" + getPresenterId() +
            ", totalTechnicalPoints=" + getTotalTechnicalPoints() +
            ", averageTechnicalPoints=" + getAverageTechnicalPoints() +
            ", totalSpeakingPoints=" + getTotalSpeakingPoints() +
            ", averageSpeakingPoints=" + getAverageSpeakingPoints() +
            ", totalExcitementPoints=" + getTotalExcitementPoints() +
            ", averageExcitementPoints=" + getAverageExcitementPoints() +
            "}";
    }
}
