package io.urla.conferuns.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Place.
 */
@Entity
@Table(name = "place")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "street")
    private String street;

    @Column(name = "door_no")
    private String doorNo;

    @OneToMany(mappedBy = "place")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany(mappedBy = "places")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Conference> conferences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public Place placeId(Long placeId) {
        this.placeId = placeId;
        return this;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public Place name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public Place country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public Place state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public Place city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public Place district(String district) {
        this.district = district;
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public Place street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public Place doorNo(String doorNo) {
        this.doorNo = doorNo;
        return this;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Place rooms(Set<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public Place addRooms(Room room) {
        this.rooms.add(room);
        room.setPlace(this);
        return this;
    }

    public Place removeRooms(Room room) {
        this.rooms.remove(room);
        room.setPlace(null);
        return this;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Conference> getConferences() {
        return conferences;
    }

    public Place conferences(Set<Conference> conferences) {
        this.conferences = conferences;
        return this;
    }

    public Place addConferences(Conference conference) {
        this.conferences.add(conference);
        conference.getPlaces().add(this);
        return this;
    }

    public Place removeConferences(Conference conference) {
        this.conferences.remove(conference);
        conference.getPlaces().remove(this);
        return this;
    }

    public void setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Place)) {
            return false;
        }
        return id != null && id.equals(((Place) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Place{" +
            "id=" + getId() +
            ", placeId=" + getPlaceId() +
            ", name='" + getName() + "'" +
            ", country='" + getCountry() + "'" +
            ", state='" + getState() + "'" +
            ", city='" + getCity() + "'" +
            ", district='" + getDistrict() + "'" +
            ", street='" + getStreet() + "'" +
            ", doorNo='" + getDoorNo() + "'" +
            "}";
    }
}
