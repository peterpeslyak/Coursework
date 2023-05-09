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
import java.util.*;

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
    public List<Room> getAllAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        List<Room> rooms = roomRepo.findAll(); // Все номера
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
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

        if (checkInDate.after(checkOutDate)) {
            return availableRooms;
        }

        List<Room> rooms = roomRepo.findAll(); // Все номера
        System.out.println(rooms);
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase("Penthouse") || (room.isAvailable() && room.getCapacity() >= capacity && room.getCapacity() <= capacity + 1)) { // Проверка на соответствие параметрам поиска
                System.out.println("Parameters ++ " + room);
                boolean isBooked = false;
                for (Reservation reservation : room.getReservationList()) {
                    Date reservationCheckInDate = reservation.getCheckInDate();
                    Date reservationCheckOutDate = reservation.getCheckOutDate();

                    if (reservationCheckInDate == null || reservationCheckOutDate == null) {
                        continue;
                    }

                    if ((checkInDate.before(reservationCheckOutDate) && checkOutDate.after(reservationCheckOutDate)) ||
                            (checkInDate.before(reservationCheckInDate) && checkOutDate.after(reservationCheckInDate)) ||
                            (checkInDate.after(reservationCheckInDate) && checkOutDate.before(reservationCheckOutDate)) ||
                            (checkInDate.equals(reservationCheckInDate) && checkOutDate.equals(reservationCheckOutDate)) ||
                            (checkInDate.after(reservationCheckInDate) && checkOutDate.equals(reservationCheckOutDate)) ||
                            (checkInDate.equals(reservationCheckInDate) && checkOutDate.before(reservationCheckOutDate))
                    ) {
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


    public Room findAvailableRoom(Long idRoom, Date checkInDate, Date checkOutDate) {
        // Находим номер по его idRoom и проверяем доступность в указанный период
        Optional<Room> roomOptional = roomRepo.findById(idRoom);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
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
                System.out.println("Is available " + room.getIdRoom());
                return room;
            }
        }
        return null;
    }
}
