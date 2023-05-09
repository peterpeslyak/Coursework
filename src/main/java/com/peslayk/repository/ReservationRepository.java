package com.peslayk.repository;

import com.peslayk.model.Reservation;
import com.peslayk.model.Room;
import com.peslayk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository <Reservation, Long> {

    List<Reservation> findByUser(User user);
}

