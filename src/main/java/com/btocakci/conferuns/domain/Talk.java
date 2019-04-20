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

import com.btocakci.conferuns.domain.enumeration.Language;

import com.btocakci.conferuns.domain.enumeration.TalkStatus;

/**
 * A Talk.
 */
@Entity
@Table(name = "talk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "talk")
public class Talk implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Column(name = "main_topic")
    private String mainTopic;

    @Column(name = "sub_topic")
    private String subTopic;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TalkStatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private Presenter presenter;

    @OneToMany(mappedBy = "talk")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<File> files = new HashSet<>();
    @ManyToMany(mappedBy = "talks")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TalkParticipant> participants = new HashSet<>();

    @ManyToMany(mappedBy = "talks")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Conference> conferences = new HashSet<>();

    @ManyToMany(mappedBy = "talks")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TalkTag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public Talk language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getMainTopic() {
        return mainTopic;
    }

    public Talk mainTopic(String mainTopic) {
        this.mainTopic = mainTopic;
        return this;
    }

    public void setMainTopic(String mainTopic) {
        this.mainTopic = mainTopic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public Talk subTopic(String subTopic) {
        this.subTopic = subTopic;
        return this;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public TalkStatus getStatus() {
        return status;
    }

    public Talk status(TalkStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TalkStatus status) {
        this.status = status;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public Talk presenter(Presenter presenter) {
        this.presenter = presenter;
        return this;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public Set<File> getFiles() {
        return files;
    }

    public Talk files(Set<File> files) {
        this.files = files;
        return this;
    }

    public Talk addFiles(File file) {
        this.files.add(file);
        file.setTalk(this);
        return this;
    }

    public Talk removeFiles(File file) {
        this.files.remove(file);
        file.setTalk(null);
        return this;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Set<TalkParticipant> getParticipants() {
        return participants;
    }

    public Talk participants(Set<TalkParticipant> talkParticipants) {
        this.participants = talkParticipants;
        return this;
    }

    public Talk addParticipants(TalkParticipant talkParticipant) {
        this.participants.add(talkParticipant);
        talkParticipant.getTalks().add(this);
        return this;
    }

    public Talk removeParticipants(TalkParticipant talkParticipant) {
        this.participants.remove(talkParticipant);
        talkParticipant.getTalks().remove(this);
        return this;
    }

    public void setParticipants(Set<TalkParticipant> talkParticipants) {
        this.participants = talkParticipants;
    }

    public Set<Conference> getConferences() {
        return conferences;
    }

    public Talk conferences(Set<Conference> conferences) {
        this.conferences = conferences;
        return this;
    }

    public Talk addConferences(Conference conference) {
        this.conferences.add(conference);
        conference.getTalks().add(this);
        return this;
    }

    public Talk removeConferences(Conference conference) {
        this.conferences.remove(conference);
        conference.getTalks().remove(this);
        return this;
    }

    public void setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
    }

    public Set<TalkTag> getTags() {
        return tags;
    }

    public Talk tags(Set<TalkTag> talkTags) {
        this.tags = talkTags;
        return this;
    }

    public Talk addTags(TalkTag talkTag) {
        this.tags.add(talkTag);
        talkTag.getTalks().add(this);
        return this;
    }

    public Talk removeTags(TalkTag talkTag) {
        this.tags.remove(talkTag);
        talkTag.getTalks().remove(this);
        return this;
    }

    public void setTags(Set<TalkTag> talkTags) {
        this.tags = talkTags;
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
        Talk talk = (Talk) o;
        if (talk.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), talk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Talk{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", mainTopic='" + getMainTopic() + "'" +
            ", subTopic='" + getSubTopic() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
