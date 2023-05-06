package com.peslayk.service;

import com.peslayk.model.Reservation;
import com.peslayk.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    ReservationRepository reservationRepo;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }
}
