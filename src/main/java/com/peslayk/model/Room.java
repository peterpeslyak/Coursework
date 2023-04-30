package com.peslayk.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue //(strategy = GenerationType.AUTO)
    private Long idRoom;
    @OneToOne
    @JoinColumn(name = "idAmenities")
    private Amenities amenities;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservationList;

    @OneToMany(mappedBy = "room")
    private List<Reviews> reviews;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private boolean available;
    @Column(nullable = false)
    private String roomNumber;


    public Room(Amenities amenities, List<Reservation> reservationList, List<Reviews> reviews, String name,
                String type, String description, Integer capacity, Double price, boolean available, String roomNumber) {
        this.amenities = amenities;
        this.reservationList = reservationList;
        this.reviews = reviews;
        this.name = name;
        this.type = type;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.available = available;
        this.roomNumber = roomNumber;
    }

    public Room() {
    }

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Amenities getAmenities() {
        return amenities;
    }

    public void setAmenities(Amenities amenities) {
        this.amenities = amenities;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }
}

