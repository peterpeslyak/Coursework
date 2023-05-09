package com.peslayk.service;

import com.peslayk.model.Reservation;
import com.peslayk.model.Room;
import com.peslayk.model.User;
import com.peslayk.repository.ReservationRepository;
import com.peslayk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    ReservationRepository reservationRepo;

    @Autowired
    RoomService roomService;

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    @Override
    public Reservation newReservation(String checkIn, String checkOut, Room room, User user, Integer guests, Double priceTotal) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date checkInDate = dateFormat.parse(checkIn);
            Date checkOutDate = dateFormat.parse(checkOut);

            List<Room> availableRooms = roomService.findAvailableRooms(checkInDate, checkOutDate, guests);

            boolean isAvailable = false;

            for (Room room1 : availableRooms) {
                if (room1.getIdRoom().equals(room.getIdRoom())) {
                    isAvailable = true;
                }
            }

            if(isAvailable){
                Reservation reservation = new Reservation();

                reservation.setCheckInDate(checkInDate);
                reservation.setCheckOutDate(checkOutDate);
                reservation.setUser(user);
                reservation.setRoom(room);
                reservation.setGuestsCount(guests);
                reservation.setTotalCost(priceTotal);
                reservation.setStatus("NEW");

                return reservationRepo.save(reservation);
            } else {
                return null;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
