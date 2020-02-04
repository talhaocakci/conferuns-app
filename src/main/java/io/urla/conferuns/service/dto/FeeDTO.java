package io.urla.conferuns.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import io.urla.conferuns.domain.enumeration.SpecialParticipantType;

/**
 * A DTO for the {@link io.urla.conferuns.domain.Fee} entity.
 */
public class FeeDTO implements Serializable {

    private Long id;

    private Long conferenceId;

    private String feeLabel;

    private Instant fromTime;

    private Instant tillTime;

    private Double price;

    private SpecialParticipantType specialTo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getFeeLabel() {
        return feeLabel;
    }

    public void setFeeLabel(String feeLabel) {
        this.feeLabel = feeLabel;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public void setFromTime(Instant fromTime) {
        this.fromTime = fromTime;
    }

    public Instant getTillTime() {
        return tillTime;
    }

    public void setTillTime(Instant tillTime) {
        this.tillTime = tillTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SpecialParticipantType getSpecialTo() {
        return specialTo;
    }

    public void setSpecialTo(SpecialParticipantType specialTo) {
        this.specialTo = specialTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeeDTO feeDTO = (FeeDTO) o;
        if (feeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeeDTO{" +
            "id=" + getId() +
            ", conferenceId=" + getConferenceId() +
            ", feeLabel='" + getFeeLabel() + "'" +
            ", fromTime='" + getFromTime() + "'" +
            ", tillTime='" + getTillTime() + "'" +
            ", price=" + getPrice() +
            ", specialTo='" + getSpecialTo() + "'" +
            "}";
    }
}
