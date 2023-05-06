package com.peslayk.service;

import com.peslayk.model.Reservation;
import com.peslayk.model.Room;
import com.peslayk.repository.ReservationRepository;
import com.peslayk.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private ReservationRepository reservationRepo;

    @Override
    public Room saveRoom(Room room) {
        return roomRepo.save(room);
    }


    @Override
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Room getRoomById(Long idRoom) {
        return roomRepo.findById(idRoom).get();
    }

    @Override
    public String deleteRoom(Long idRoom) {
        Room room = roomRepo.findById(idRoom).get();

        if(room!=null){
            roomRepo.delete(room);
            return "Product Delete Successfully";
        }
        return "Something went wrong... Try again later";
    }

    @Override
    public Room editRoom(Long idRoom, Room room) {
        // get room from DB
        Room oldRoom = getRoomById(idRoom);
        oldRoom.setIdRoom(idRoom);
        oldRoom.setName(room.getName());
        oldRoom.setBeds(room.getBeds());
        oldRoom.setRoomNumber(room.getRoomNumber());
        oldRoom.setType(room.getType());
        oldRoom.setCapacity(room.getCapacity());
        oldRoom.setPrice(room.getPrice());
        oldRoom.setDescription(room.getDescription());
        oldRoom.setAvailable(room.isAvailable());
        // save updated object
        return roomRepo.save(room);
    }


    public List<Room> findAvailableRooms(Date checkInDate, Date checkOutDate, Integer capacity) {
        List<Room> availableRooms = new ArrayList<>();

        // Находим все номера без бронирований в указанный период
        List<Room> rooms = roomRepo.findAll(); // Все номера
        System.out.println(rooms);
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase("Penthouse") || room.isAvailable() && room.getCapacity() >= capacity && room.getCapacity() <= capacity + 1) { // Проверка на соответствие параметрам поиска
                System.out.println("Parameters ++ " + room);
                boolean isBooked = false;
                for (Reservation reservation : room.getReservationList()) {
                    if ((checkInDate.before(reservation.getCheckOutDate()) && checkInDate.after(reservation.getCheckInDate())) ||
                            (checkOutDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate())) ||
                            (checkInDate.before(reservation.getCheckInDate()) && checkOutDate.after(reservation.getCheckOutDate())) ||
                            (checkInDate.equals(reservation.getCheckInDate()) && checkOutDate.equals(reservation.getCheckOutDate()))) {
                        isBooked = true;
                        System.out.println("Is booked" + room.getIdRoom());
                        break;
                    }
                }
                if (!isBooked) {
                    availableRooms.add(room);
                    System.out.println("Is available " + room.getIdRoom());
                }
            }
        }
        System.out.println(availableRooms);
        return availableRooms;
    }


}
