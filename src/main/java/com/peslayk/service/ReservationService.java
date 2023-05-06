package com.peslayk.service;

import com.peslayk.model.Reservation;
import com.peslayk.model.Room;
import com.peslayk.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ReservationService {
    List<Reservation> getAllReservations();

    Reservation newReservation(String checkIn, String checkOut, Room room, User user, Integer guests, Double priceTotal);
}
