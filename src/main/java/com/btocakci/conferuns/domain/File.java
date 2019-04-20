package com.btocakci.conferuns.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.btocakci.conferuns.domain.enumeration.FileStatus;

/**
 * A File.
 */
@Entity
@Table(name = "file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "jhi_type")
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FileStatus status;

    @ManyToOne
    @JsonIgnoreProperties("files")
    private Talk talk;

    @OneToMany(mappedBy = "file")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FileReview> reviews = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public File name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public File path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public File type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FileStatus getStatus() {
        return status;
    }

    public File status(FileStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    public Talk getTalk() {
        return talk;
    }

    public File talk(Talk talk) {
        this.talk = talk;
        return this;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    public Set<FileReview> getReviews() {
        return reviews;
    }

    public File reviews(Set<FileReview> fileReviews) {
        this.reviews = fileReviews;
        return this;
    }

    public File addReviews(FileReview fileReview) {
        this.reviews.add(fileReview);
        fileReview.setFile(this);
        return this;
    }

    public File removeReviews(FileReview fileReview) {
        this.reviews.remove(fileReview);
        fileReview.setFile(null);
        return this;
    }

    public void setReviews(Set<FileReview> fileReviews) {
        this.reviews = fileReviews;
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
        File file = (File) o;
        if (file.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), file.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "File{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", path='" + getPath() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
