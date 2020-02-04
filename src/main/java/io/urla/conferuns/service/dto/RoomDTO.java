package io.urla.conferuns.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.urla.conferuns.domain.Room} entity.
 */
public class RoomDTO implements Serializable {

    private Long id;

    private Long roomId;

    private String roomName;

    private Integer floor;

    private Integer capacity;


    private Long placeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoomDTO roomDTO = (RoomDTO) o;
        if (roomDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
            "id=" + getId() +
            ", roomId=" + getRoomId() +
            ", roomName='" + getRoomName() + "'" +
            ", floor=" + getFloor() +
            ", capacity=" + getCapacity() +
            ", placeId=" + getPlaceId() +
            "}";
    }
}
