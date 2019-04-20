package com.btocakci.conferuns.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToOne
    @JsonIgnoreProperties("rooms")
    private Place place;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Room roomId(Long roomId) {
        this.roomId = roomId;
        return this;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public Room placeId(Long placeId) {
        this.placeId = placeId;
        return this;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getRoomName() {
        return roomName;
    }

    public Room roomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getFloor() {
        return floor;
    }

    public Room floor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Room capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Place getPlace() {
        return place;
    }

    public Room place(Place place) {
        this.place = place;
        return this;
    }

    public void setPlace(Place place) {
        this.place = place;
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
        Room room = (Room) o;
        if (room.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), room.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", roomId=" + getRoomId() +
            ", placeId=" + getPlaceId() +
            ", roomName='" + getRoomName() + "'" +
            ", floor=" + getFloor() +
            ", capacity=" + getCapacity() +
            "}";
    }
}
