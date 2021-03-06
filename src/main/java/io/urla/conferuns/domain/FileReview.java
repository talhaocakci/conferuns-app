package io.urla.conferuns.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import io.urla.conferuns.domain.enumeration.FileReviewStatus;

/**
 * A FileReview.
 */
@Entity
@Table(name = "file_review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "comment")
    private String comment;

    @Column(name = "reviewer")
    private String reviewer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FileReviewStatus status;

    @ManyToOne
    @JsonIgnoreProperties("reviews")
    private File file;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public FileReview date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public FileReview comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewer() {
        return reviewer;
    }

    public FileReview reviewer(String reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public FileReviewStatus getStatus() {
        return status;
    }

    public FileReview status(FileReviewStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FileReviewStatus status) {
        this.status = status;
    }

    public File getFile() {
        return file;
    }

    public FileReview file(File file) {
        this.file = file;
        return this;
    }

    public void setFile(File file) {
        this.file = file;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileReview)) {
            return false;
        }
        return id != null && id.equals(((FileReview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FileReview{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", reviewer='" + getReviewer() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
