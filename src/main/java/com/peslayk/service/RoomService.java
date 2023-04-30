package com.peslayk.service;

import com.peslayk.model.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    public Room saveRoom(Room room);

    public List<Room> getAllRooms();

    public Room getRoomById(Long idRoom);

    public String deleteRoom(Long idRoom);

    public Room editRoom(Room room);
}
