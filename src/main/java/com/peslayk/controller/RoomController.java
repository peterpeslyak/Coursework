package com.peslayk.controller;

import com.peslayk.model.Room;

import com.peslayk.model.User;
import com.peslayk.repository.RoomRepository;
import com.peslayk.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private RoomService roomService;

    @PostMapping("/saveRoom")
    public String saveRoom(@ModelAttribute("room") Room room) {
        System.out.println(room);
        roomService.saveRoom(room);
        return "redirect:/admin/rooms";
    }

    @GetMapping("admin/rooms")
    public String getAllRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "admin/rooms";
    }

    @GetMapping(value = "admin/rooms/{idRoom}")
    public String getRoomById(@PathVariable Long idRoom, Model model) {
        Room room = roomService.getRoomById(idRoom);
        model.addAttribute("room", room);
        return "room-details";
    }

    @RequestMapping(value = "admin/rooms/editRoom/{idRoom}")
    public String editRoom(@PathVariable Long idRoom, Model model) {
        model.addAttribute("room", roomService.getRoomById(idRoom));
        return "admin/editRoom";
    }

    @PostMapping(value = "/admin/rooms/editRoom/update/{idRoom}")
    public String updateRoom(@PathVariable Long idRoom,
            @ModelAttribute("room") Room room,
            Model model) {
        // get room from DB
        Room oldRoom = roomService.getRoomById(idRoom);
        oldRoom.setIdRoom(idRoom);
        oldRoom.setName(room.getName());
        oldRoom.setType(room.getType());
        oldRoom.setCapacity(room.getCapacity());
        oldRoom.setPrice(room.getPrice());
        oldRoom.setDescription(room.getDescription());
        oldRoom.setAvailable(room.isAvailable());
        // save updated object
        roomService.editRoom(oldRoom);
        return "redirect:/admin/rooms";
    }

    @GetMapping(value = "admin/rooms/deleteRoom/{idRoom}")
    public String deleteRoom(@PathVariable Long idRoom) {
        roomService.deleteRoom(idRoom);
        return "redirect:/admin/rooms";
    }

}
