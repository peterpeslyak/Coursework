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

    @GetMapping(value = "admin/rooms/deleteRoom/{idRoom}")
    public String deleteRoom(@PathVariable Long idRoom) {
        roomService.deleteRoom(idRoom);
        return "redirect:/admin/rooms";
    }

    @RequestMapping(value = "admin/rooms/editRoom/{id}")
    public String editRoom(@PathVariable("id") Long idRoom, @ModelAttribute Room room) {
        room.setIdRoom(idRoom);
        roomService.editRoom(room, idRoom);
        return "redirect:/rooms";
    }

}
