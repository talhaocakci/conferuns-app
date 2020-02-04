package io.urla.conferuns.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.urla.conferuns.domain.enumeration.ConferenceTopic;

import io.urla.conferuns.domain.enumeration.Language;

/**
 * A Conference.
 */
@Entity
@Table(name = "conference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conference_id")
    private Long conferenceId;

    @Column(name = "main_name")
    private String mainName;

    @Column(name = "sub_name")
    private String subName;

    @Enumerated(EnumType.STRING)
    @Column(name = "main_topic")
    private ConferenceTopic mainTopic;

    @Column(name = "sub_topic")
    private String subTopic;

    @Column(name = "description")
    private String description;

    @Column(name = "is_free")
    private Boolean isFree;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "last_talk_submission_time")
    private Instant lastTalkSubmissionTime;

    @OneToMany(mappedBy = "conference")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ScheduleItem> scheduleItems = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "conference_places",
               joinColumns = @JoinColumn(name = "conference_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "places_id", referencedColumnName = "id"))
    private Set<Place> places = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "conference_talks",
               joinColumns = @JoinColumn(name = "conference_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "talks_id", referencedColumnName = "id"))
    private Set<Talk> talks = new HashSet<>();

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

    public Conference conferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
        return this;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getMainName() {
        return mainName;
    }

    public Conference mainName(String mainName) {
        this.mainName = mainName;
        return this;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getSubName() {
        return subName;
    }

    public Conference subName(String subName) {
        this.subName = subName;
        return this;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public ConferenceTopic getMainTopic() {
        return mainTopic;
    }

    public Conference mainTopic(ConferenceTopic mainTopic) {
        this.mainTopic = mainTopic;
        return this;
    }

    public void setMainTopic(ConferenceTopic mainTopic) {
        this.mainTopic = mainTopic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public Conference subTopic(String subTopic) {
        this.subTopic = subTopic;
        return this;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public String getDescription() {
        return description;
    }

    public Conference description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsFree() {
        return isFree;
    }

    public Conference isFree(Boolean isFree) {
        this.isFree = isFree;
        return this;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Language getLanguage() {
        return language;
    }

    public Conference language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Conference startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Conference endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getLastTalkSubmissionTime() {
        return lastTalkSubmissionTime;
    }

    public Conference lastTalkSubmissionTime(Instant lastTalkSubmissionTime) {
        this.lastTalkSubmissionTime = lastTalkSubmissionTime;
        return this;
    }

    public void setLastTalkSubmissionTime(Instant lastTalkSubmissionTime) {
        this.lastTalkSubmissionTime = lastTalkSubmissionTime;
    }

    public Set<ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public Conference scheduleItems(Set<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
        return this;
    }

    public Conference addScheduleItems(ScheduleItem scheduleItem) {
        this.scheduleItems.add(scheduleItem);
        scheduleItem.setConference(this);
        return this;
    }

    public Conference removeScheduleItems(ScheduleItem scheduleItem) {
        this.scheduleItems.remove(scheduleItem);
        scheduleItem.setConference(null);
        return this;
    }

    public void setScheduleItems(Set<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public Conference places(Set<Place> places) {
        this.places = places;
        return this;
    }

    public Conference addPlaces(Place place) {
        this.places.add(place);
        place.getConferences().add(this);
        return this;
    }

    public Conference removePlaces(Place place) {
        this.places.remove(place);
        place.getConferences().remove(this);
        return this;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public Conference talks(Set<Talk> talks) {
        this.talks = talks;
        return this;
    }

    public Conference addTalks(Talk talk) {
        this.talks.add(talk);
        talk.getConferences().add(this);
        return this;
    }

    public Conference removeTalks(Talk talk) {
        this.talks.remove(talk);
        talk.getConferences().remove(this);
        return this;
    }

    public void setTalks(Set<Talk> talks) {
        this.talks = talks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conference)) {
            return false;
        }
        return id != null && id.equals(((Conference) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Conference{" +
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
