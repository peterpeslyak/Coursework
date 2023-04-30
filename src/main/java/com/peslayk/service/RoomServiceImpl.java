package com.peslayk.service;

import com.peslayk.model.Room;
import com.peslayk.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepo;

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
    public Room editRoom(Room room) {
        return roomRepo.save(room);
    }
}
