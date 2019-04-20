package com.btocakci.conferuns.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.btocakci.conferuns.domain.enumeration.ConferenceTopic;

import com.btocakci.conferuns.domain.enumeration.Audience;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "subject")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "topic")
    private ConferenceTopic topic;

    @Column(name = "difficulty")
    private Integer difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "audience")
    private Audience audience;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConferenceTopic getTopic() {
        return topic;
    }

    public Subject topic(ConferenceTopic topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(ConferenceTopic topic) {
        this.topic = topic;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Subject difficulty(Integer difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Audience getAudience() {
        return audience;
    }

    public Subject audience(Audience audience) {
        this.audience = audience;
        return this;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
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
        Subject subject = (Subject) o;
        if (subject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", topic='" + getTopic() + "'" +
            ", difficulty=" + getDifficulty() +
            ", audience='" + getAudience() + "'" +
            "}";
    }
}
