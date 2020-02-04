package io.urla.conferuns.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TalkHistory.
 */
@Entity
@Table(name = "talk_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalkHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "total_audience")
    private Long totalAudience;

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
    private Talk talk;

    @ManyToOne
    @JsonIgnoreProperties("talks")
    private Presenter presenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public TalkHistory date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getTotalAudience() {
        return totalAudience;
    }

    public TalkHistory totalAudience(Long totalAudience) {
        this.totalAudience = totalAudience;
        return this;
    }

    public void setTotalAudience(Long totalAudience) {
        this.totalAudience = totalAudience;
    }

    public Double getTotalTechnicalPoints() {
        return totalTechnicalPoints;
    }

    public TalkHistory totalTechnicalPoints(Double totalTechnicalPoints) {
        this.totalTechnicalPoints = totalTechnicalPoints;
        return this;
    }

    public void setTotalTechnicalPoints(Double totalTechnicalPoints) {
        this.totalTechnicalPoints = totalTechnicalPoints;
    }

    public Double getAverageTechnicalPoints() {
        return averageTechnicalPoints;
    }

    public TalkHistory averageTechnicalPoints(Double averageTechnicalPoints) {
        this.averageTechnicalPoints = averageTechnicalPoints;
        return this;
    }

    public void setAverageTechnicalPoints(Double averageTechnicalPoints) {
        this.averageTechnicalPoints = averageTechnicalPoints;
    }

    public Double getTotalSpeakingPoints() {
        return totalSpeakingPoints;
    }

    public TalkHistory totalSpeakingPoints(Double totalSpeakingPoints) {
        this.totalSpeakingPoints = totalSpeakingPoints;
        return this;
    }

    public void setTotalSpeakingPoints(Double totalSpeakingPoints) {
        this.totalSpeakingPoints = totalSpeakingPoints;
    }

    public Double getAverageSpeakingPoints() {
        return averageSpeakingPoints;
    }

    public TalkHistory averageSpeakingPoints(Double averageSpeakingPoints) {
        this.averageSpeakingPoints = averageSpeakingPoints;
        return this;
    }

    public void setAverageSpeakingPoints(Double averageSpeakingPoints) {
        this.averageSpeakingPoints = averageSpeakingPoints;
    }

    public Double getTotalExcitementPoints() {
        return totalExcitementPoints;
    }

    public TalkHistory totalExcitementPoints(Double totalExcitementPoints) {
        this.totalExcitementPoints = totalExcitementPoints;
        return this;
    }

    public void setTotalExcitementPoints(Double totalExcitementPoints) {
        this.totalExcitementPoints = totalExcitementPoints;
    }

    public Double getAverageExcitementPoints() {
        return averageExcitementPoints;
    }

    public TalkHistory averageExcitementPoints(Double averageExcitementPoints) {
        this.averageExcitementPoints = averageExcitementPoints;
        return this;
    }

    public void setAverageExcitementPoints(Double averageExcitementPoints) {
        this.averageExcitementPoints = averageExcitementPoints;
    }

    public Talk getTalk() {
        return talk;
    }

    public TalkHistory talk(Talk talk) {
        this.talk = talk;
        return this;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public TalkHistory presenter(Presenter presenter) {
        this.presenter = presenter;
        return this;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TalkHistory)) {
            return false;
        }
        return id != null && id.equals(((TalkHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TalkHistory{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", totalAudience=" + getTotalAudience() +
            ", totalTechnicalPoints=" + getTotalTechnicalPoints() +
            ", averageTechnicalPoints=" + getAverageTechnicalPoints() +
            ", totalSpeakingPoints=" + getTotalSpeakingPoints() +
            ", averageSpeakingPoints=" + getAverageSpeakingPoints() +
            ", totalExcitementPoints=" + getTotalExcitementPoints() +
            ", averageExcitementPoints=" + getAverageExcitementPoints() +
            "}";
    }
}
