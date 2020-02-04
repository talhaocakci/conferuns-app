package io.urla.conferuns.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ScheduleItem.
 */
@Entity
@Table(name = "schedule_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ScheduleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "till_time")
    private Instant tillTime;

    @OneToOne
    @JoinColumn(unique = true)
    private Talk talk;

    @ManyToOne
    @JsonIgnoreProperties("scheduleItems")
    private Conference conference;

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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleItem)) {
            return false;
        }
        return id != null && id.equals(((ScheduleItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
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
