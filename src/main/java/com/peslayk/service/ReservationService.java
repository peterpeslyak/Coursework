package com.peslayk.service;

import com.peslayk.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {
    List<Reservation> getAllReservations();
}
