package com.peslayk.service;

import com.peslayk.model.Room;
import com.peslayk.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room addNewRoom(Room room){
        return roomRepository.save(room);
    }
}
