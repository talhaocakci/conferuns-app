package io.urla.conferuns.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TalkTag.
 */
@Entity
@Table(name = "talk_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalkTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag")
    private String tag;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "talk_tag_talk",
               joinColumns = @JoinColumn(name = "talk_tag_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "talk_id", referencedColumnName = "id"))
    private Set<Talk> talks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public TalkTag tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public TalkTag talks(Set<Talk> talks) {
        this.talks = talks;
        return this;
    }

    public TalkTag addTalk(Talk talk) {
        this.talks.add(talk);
        talk.getTags().add(this);
        return this;
    }

    public TalkTag removeTalk(Talk talk) {
        this.talks.remove(talk);
        talk.getTags().remove(this);
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
        if (!(o instanceof TalkTag)) {
            return false;
        }
        return id != null && id.equals(((TalkTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TalkTag{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            "}";
    }
}
