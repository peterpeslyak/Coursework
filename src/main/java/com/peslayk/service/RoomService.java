package com.peslayk.service;

import com.peslayk.model.Room;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public interface RoomService {

    //public Room saveRoom(Room room);

    public Room saveRoom(Room room);

    public List<Room> getAllRooms();

    public Room getRoomById(Long idRoom);

    public String deleteRoom(Long idRoom);

    public Room editRoom(Long idRoom, Room room);

    public List<Room> findAvailableRooms(Date checkIn, Date checkOut, Integer capacity);
}
