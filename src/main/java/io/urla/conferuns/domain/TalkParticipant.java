package io.urla.conferuns.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TalkParticipant.
 */
@Entity
@Table(name = "talk_participant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalkParticipant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "checked_in")
    private Boolean checkedIn;

    @Column(name = "planned_to_go")
    private Boolean plannedToGo;

    @Column(name = "favorited")
    private Boolean favorited;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "talk_participant_talks",
               joinColumns = @JoinColumn(name = "talk_participant_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "talks_id", referencedColumnName = "id"))
    private Set<Talk> talks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCheckedIn() {
        return checkedIn;
    }

    public TalkParticipant checkedIn(Boolean checkedIn) {
        this.checkedIn = checkedIn;
        return this;
    }

    public void setCheckedIn(Boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Boolean isPlannedToGo() {
        return plannedToGo;
    }

    public TalkParticipant plannedToGo(Boolean plannedToGo) {
        this.plannedToGo = plannedToGo;
        return this;
    }

    public void setPlannedToGo(Boolean plannedToGo) {
        this.plannedToGo = plannedToGo;
    }

    public Boolean isFavorited() {
        return favorited;
    }

    public TalkParticipant favorited(Boolean favorited) {
        this.favorited = favorited;
        return this;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public TalkParticipant talks(Set<Talk> talks) {
        this.talks = talks;
        return this;
    }

    public TalkParticipant addTalks(Talk talk) {
        this.talks.add(talk);
        talk.getParticipants().add(this);
        return this;
    }

    public TalkParticipant removeTalks(Talk talk) {
        this.talks.remove(talk);
        talk.getParticipants().remove(this);
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
        if (!(o instanceof TalkParticipant)) {
            return false;
        }
        return id != null && id.equals(((TalkParticipant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TalkParticipant{" +
            "id=" + getId() +
            ", checkedIn='" + isCheckedIn() + "'" +
            ", plannedToGo='" + isPlannedToGo() + "'" +
            ", favorited='" + isFavorited() + "'" +
            "}";
    }
}
