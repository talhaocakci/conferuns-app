package com.btocakci.conferuns.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.btocakci.conferuns.domain.enumeration.SpecialParticipantType;

/**
 * A Fee.
 */
@Entity
@Table(name = "fee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fee")
public class Fee implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conference_id")
    private Long conferenceId;

    @Column(name = "fee_label")
    private String feeLabel;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "till_time")
    private Instant tillTime;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "special_to")
    private SpecialParticipantType specialTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public Fee conferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
        return this;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getFeeLabel() {
        return feeLabel;
    }

    public Fee feeLabel(String feeLabel) {
        this.feeLabel = feeLabel;
        return this;
    }

    public void setFeeLabel(String feeLabel) {
        this.feeLabel = feeLabel;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public Fee fromTime(Instant fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public void setFromTime(Instant fromTime) {
        this.fromTime = fromTime;
    }

    public Instant getTillTime() {
        return tillTime;
    }

    public Fee tillTime(Instant tillTime) {
        this.tillTime = tillTime;
        return this;
    }

    public void setTillTime(Instant tillTime) {
        this.tillTime = tillTime;
    }

    public Double getPrice() {
        return price;
    }

    public Fee price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SpecialParticipantType getSpecialTo() {
        return specialTo;
    }

    public Fee specialTo(SpecialParticipantType specialTo) {
        this.specialTo = specialTo;
        return this;
    }

    public void setSpecialTo(SpecialParticipantType specialTo) {
        this.specialTo = specialTo;
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
        Fee fee = (Fee) o;
        if (fee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fee{" +
            "id=" + getId() +
            ", conferenceId=" + getConferenceId() +
            ", feeLabel='" + getFeeLabel() + "'" +
            ", fromTime='" + getFromTime() + "'" +
            ", tillTime='" + getTillTime() + "'" +
            ", price=" + getPrice() +
            ", specialTo='" + getSpecialTo() + "'" +
            "}";
    }
}
