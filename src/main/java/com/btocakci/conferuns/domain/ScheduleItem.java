package com.btocakci.conferuns.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ScheduleItem.
 */
@Entity
@Table(name = "schedule_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "scheduleitem")
public class ScheduleItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "till_time")
    private Instant tillTime;

    @ManyToOne
    @JsonIgnoreProperties("scheduleItems")
    private Conference conference;

    @OneToOne
    @JoinColumn(unique = true)
    private Talk talk;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public ScheduleItem fromTime(Instant fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public void setFromTime(Instant fromTime) {
        this.fromTime = fromTime;
    }

    public Instant getTillTime() {
        return tillTime;
    }

    public ScheduleItem tillTime(Instant tillTime) {
        this.tillTime = tillTime;
        return this;
    }

    public void setTillTime(Instant tillTime) {
        this.tillTime = tillTime;
    }

    public Conference getConference() {
        return conference;
    }

    public ScheduleItem conference(Conference conference) {
        this.conference = conference;
        return this;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Talk getTalk() {
        return talk;
    }

    public ScheduleItem talk(Talk talk) {
        this.talk = talk;
        return this;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
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
        ScheduleItem scheduleItem = (ScheduleItem) o;
        if (scheduleItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheduleItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScheduleItem{" +
            "id=" + getId() +
            ", fromTime='" + getFromTime() + "'" +
            ", tillTime='" + getTillTime() + "'" +
            "}";
    }
}
