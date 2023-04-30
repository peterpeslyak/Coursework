package com.peslayk.model;

import javax.persistence.*;

@Entity
@Table(name = "room_amenities")
public class Amenities {

    @Id
    @GeneratedValue
    private Long idAmenities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "air_conditioner")
    private Boolean airConditioner;

    @Column(name = "tv")
    private Boolean tv;

    @Column(name = "minibar")
    private Boolean minibar;

    @Column(name = "bathroom")
    private Boolean bathroom;

    @Column(name = "wifi")
    private Boolean wifi;

    @Column(name = "restaurant")
    private Boolean restaurant;

    @Column(name = "spa")
    private Boolean spa;

    @Column(name = "pool")
    private Boolean pool;

    @Column(name = "parking")
    private Boolean parking;

    public Amenities(Room room, Boolean airConditioner, Boolean tv, Boolean minibar, Boolean bathroom, Boolean wifi,
                     Boolean restaurant, Boolean spa, Boolean pool, Boolean parking) {
        this.room = room;
        this.airConditioner = airConditioner;
        this.tv = tv;
        this.minibar = minibar;
        this.bathroom = bathroom;
        this.wifi = wifi;
        this.restaurant = restaurant;
        this.spa = spa;
        this.pool = pool;
        this.parking = parking;
    }

    public Amenities() {

    }

    public Long getIdAmenities() {
        return idAmenities;
    }

    public void setIdAmenities(Long idAmenities) {
        this.idAmenities = idAmenities;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean getAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(Boolean airConditioner) {
        this.airConditioner = airConditioner;
    }

    public Boolean getTv() {
        return tv;
    }

    public void setTv(Boolean tv) {
        this.tv = tv;
    }

    public Boolean getMinibar() {
        return minibar;
    }

    public void setMinibar(Boolean minibar) {
        this.minibar = minibar;
    }

    public Boolean getBathroom() {
        return bathroom;
    }

    public void setBathroom(Boolean bathroom) {
        this.bathroom = bathroom;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Boolean restaurant) {
        this.restaurant = restaurant;
    }

    public Boolean getSpa() {
        return spa;
    }

    public void setSpa(Boolean spa) {
        this.spa = spa;
    }

    public Boolean getPool() {
        return pool;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }
}
