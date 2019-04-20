package com.btocakci.conferuns.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TalkTag entity.
 */
public class TalkTagDTO implements Serializable {

    private Long id;

    private String tag;


    private Set<TalkDTO> talks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<TalkDTO> getTalks() {
        return talks;
    }

    public void setTalks(Set<TalkDTO> talks) {
        this.talks = talks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TalkTagDTO talkTagDTO = (TalkTagDTO) o;
        if (talkTagDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), talkTagDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TalkTagDTO{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            "}";
    }
}
