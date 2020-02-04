package io.urla.conferuns.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import io.urla.conferuns.domain.enumeration.FileReviewStatus;

/**
 * A DTO for the {@link io.urla.conferuns.domain.FileReview} entity.
 */
public class FileReviewDTO implements Serializable {

    private Long id;

    private Instant date;

    private String comment;

    private String reviewer;

    private FileReviewStatus status;


    private Long fileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public FileReviewStatus getStatus() {
        return status;
    }

    public void setStatus(FileReviewStatus status) {
        this.status = status;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileReviewDTO fileReviewDTO = (FileReviewDTO) o;
        if (fileReviewDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileReviewDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileReviewDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", reviewer='" + getReviewer() + "'" +
            ", status='" + getStatus() + "'" +
            ", fileId=" + getFileId() +
            "}";
    }
}
